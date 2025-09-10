package org.easywork.console.infra.repository.base;

import cn.hutool.core.util.ReflectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.easywork.common.rest.request.PageQuery;
import org.easywork.console.domain.model.base.BaseEntity;
import org.easywork.console.domain.repository.base.BaseRepository;
import org.easywork.console.infra.repository.po.base.BasePO;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * 基础仓储实现类
 * 提供通用的 persist 和 findById 方法，减少重复代码
 *
 * @author fiveupup
 * @version 1.0.0
 * @date 2025/09/10
 */
public abstract class BaseRepositoryImpl<M extends BaseMapper<T>, T extends BasePO, B extends BaseEntity, Q extends PageQuery>
        extends ServiceImpl<M, T>
        implements BaseRepository<B, Q> {

    /**
     * 实体类型，通过反射获取泛型参数
     */
    private final Class<T> entityClass;

    /**
     * 业务实体类型，通过反射获取泛型参数
     */
    private final Class<B> businessEntityClass;

    /**
     * 转换器实例，通过反射获取
     */
    private final Object converter;

    @SuppressWarnings("unchecked")
    public BaseRepositoryImpl() {
        // 第二个泛型参数是 T (PO类型)
        this.entityClass = (Class<T>) getGenericType(1);
        // 第三个泛型参数是 B (业务实体类型)
        this.businessEntityClass = (Class<B>) getGenericType(2);
        // 初始化转换器
        this.converter = initConverter();
    }

    protected LambdaQueryWrapper<T> queryWrapper() {
        LambdaQueryWrapper<T> queryWrapper = Wrappers.lambdaQuery(entityClass);
        queryWrapper.eq(BasePO::getDeleted, 0);
        return queryWrapper;
    }


    /**
     * 为新增操作准备实体
     * 设置创建时间、删除标志、版本号等默认值
     *
     * @param entity 实体对象
     */
    protected void prepareForInsert(T entity) {
        LocalDateTime now = LocalDateTime.now();
        entity.setCreateTime(now);
        entity.setDeleted(0);
        entity.setVersion(0);
        // 可以在这里设置创建人等信息
        // entity.setCreateBy(getCurrentUserId());
    }

    /**
     * 为更新操作准备实体
     * 设置更新时间等字段
     *
     * @param entity 实体对象
     */
    protected void prepareForUpdate(T entity) {
        LocalDateTime now = LocalDateTime.now();
        entity.setUpdateTime(now);
        // 可以在这里设置更新人等信息
        // entity.setUpdateBy(getCurrentUserId());
    }

    /**
     * 通用逻辑删除方法
     *
     * @param id 要删除的实体ID
     */
    protected void logicalDeleteById(Long id) {
        if (id == null) {
            return;
        }

        try {
            T entity = entityClass.getDeclaredConstructor().newInstance();
            entity.setId(id);
            entity.setDeleted(1);
            entity.setUpdateTime(LocalDateTime.now());
            super.updateById(entity);
        } catch (Exception e) {
            throw new RuntimeException("逻辑删除失败", e);
        }
    }


    /**
     * 获取泛型参数的实际类型
     *
     * @param index 泛型参数索引，0 表示第一个，1 表示第二个
     * @return 泛型参数的 Class 对象
     */
    private Class<?> getGenericType(int index) {
        // 获取当前类的直接父类的泛型信息
        Type superClass = getClass().getGenericSuperclass();
        if (superClass instanceof ParameterizedType parameterizedType) {
            Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
            if (actualTypeArguments.length > index) {
                Type actualType = actualTypeArguments[index];
                if (actualType instanceof Class) {
                    return (Class<?>) actualType;
                }
            }
        }

        throw new IllegalStateException("无法获取泛型参数类型，请确保子类正确继承了 BaseRepositoryImpl");
    }

    @Override
    public void deleteById(Long id) {
        this.logicalDeleteById(id);
    }

    @Override
    public void deleteByIds(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return;
        }
        // 批量逻辑删除
        LambdaQueryWrapper<T> queryWrapper = Wrappers.lambdaQuery(entityClass);
        queryWrapper.in(BasePO::getId, ids);

        T updateEntity = ReflectUtil.newInstance(entityClass);
        updateEntity.setDeleted(1);
        updateEntity.setUpdateTime(LocalDateTime.now());

        this.update(updateEntity, queryWrapper);
    }

    /**
     * 通用的持久化方法
     * 子类可以直接调用这个方法实现 persist 功能
     *
     * @param entity 业务实体
     * @return 持久化后的业务实体
     */
    @Override
    public B persist(B entity) {
        // 转换为 PO 对象
        T po = convertToPO(entity);
        // 调用通用保存方法
        T savedPo = doPersist(po);
        // 转换回业务实体
        return convertToDomain(savedPo);
    }


    /**
     * 通用保存方法
     * 自动处理新增和更新时的时间戳、删除标志、版本号等字段
     *
     * @param entity 要保存的实体对象
     * @return 保存后的实体对象
     */
    protected T doPersist(T entity) {
        if (entity.getId() == null) {
            // 新增操作
            prepareForInsert(entity);
            super.save(entity);
        } else {
            // 更新操作
            prepareForUpdate(entity);
            super.updateById(entity);
        }
        return entity;
    }

    /**
     * 通用的根据ID查找方法
     * 子类可以直接调用这个方法实现 findById 功能
     *
     * @param id 实体ID
     * @return 业务实体的Optional包装
     */
    @Override
    public Optional<B> findById(Long id) {
        if (id == null) {
            return Optional.empty();
        }

        LambdaQueryWrapper<T> queryWrapper = queryWrapper();
        queryWrapper.eq(BasePO::getId, id);

        T po = super.getOne(queryWrapper);
        return Optional.ofNullable(po).map(this::convertToDomain);
    }

    /**
     * 初始化转换器实例
     * 通过反射获取对应的转换器
     *
     * @return 转换器实例
     */
    private Object initConverter() {
        try {
            // 构建转换器类名：如 User -> UserConverter
            String entityName = businessEntityClass.getSimpleName();
            String converterClassName = "org.easywork.console.infra.repository.converter." + entityName + "Converter";
            
            Class<?> converterClass = Class.forName(converterClassName);
            Field instanceField = converterClass.getDeclaredField("INSTANCE");
            return instanceField.get(null);
        } catch (Exception e) {
            throw new RuntimeException("无法初始化转换器，请确保转换器类存在：" + businessEntityClass.getSimpleName() + "Converter", e);
        }
    }

    /**
     * 将业务实体转换为 PO 对象
     * 通过反射调用转换器的 toRepository 方法
     *
     * @param entity 业务实体
     * @return PO 对象
     */
    @SuppressWarnings("unchecked")
    protected T convertToPO(B entity) {
        if (entity == null) {
            return null;
        }
        try {
            Method method = converter.getClass().getMethod("toRepository", businessEntityClass);
            return (T) method.invoke(converter, entity);
        } catch (Exception e) {
            throw new RuntimeException("转换业务实体到 PO 对象失败", e);
        }
    }

    /**
     * 将 PO 对象转换为业务实体
     * 通过反射调用转换器的 toDomain 方法
     *
     * @param po PO 对象
     * @return 业务实体
     */
    @SuppressWarnings("unchecked")
    protected B convertToDomain(T po) {
        if (po == null) {
            return null;
        }
        try {
            Method method = converter.getClass().getMethod("toDomain", entityClass);
            return (B) method.invoke(converter, po);
        } catch (Exception e) {
            throw new RuntimeException("转换 PO 对象到业务实体失败", e);
        }
    }
}
