package org.easywork.console.infra.repository.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.easywork.console.infra.repository.po.base.BasePO;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.time.LocalDateTime;

/**
 * 基础仓储实现类
 * 提供通用的 save 方法，减少重复代码
 *
 * @author fiveupup
 * @version 1.0.0
 * @date 2025/09/10
 */
public abstract class BaseRepositoryImpl<M extends BaseMapper<T>, T extends BasePO> extends ServiceImpl<M, T> {

    /**
     * 实体类型，通过反射获取泛型参数
     */
    private final Class<T> entityClass;

    @SuppressWarnings("unchecked")
    public BaseRepositoryImpl() {
        // 第二个泛型参数是 T
        this.entityClass = (Class<T>) getGenericType(1);
    }

    /**
     * 通用保存方法
     * 自动处理新增和更新时的时间戳、删除标志、版本号等字段
     *
     * @param entity 要保存的实体对象
     * @return 保存后的实体对象
     */
    protected T savePo(T entity) {
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
}
