package org.easywork.console.domain.repository.base;

import org.easywork.common.rest.request.PageQuery;
import org.easywork.common.rest.result.PageInfo;
import org.easywork.console.domain.model.base.BaseEntity;

import java.util.List;
import java.util.Optional;

/**
 * 基础仓储接口
 * 提供通用的CRUD操作方法
 *
 * @param <B> 实体类型，必须继承BaseEntity
 * @author fiveupup
 * @version 1.0.0
 * @date 2025/09/10
 */
public interface BaseRepository<B extends BaseEntity, Q extends PageQuery> {

    /**
     * 保存实体（新增或更新）
     * 如果实体ID为空则新增，否则更新
     *
     * @param entity 要保存的实体
     * @return 保存后的实体
     */
    B persist(B entity);

    /**
     * 根据ID查找实体
     *
     * @param id 实体ID
     * @return 实体的Optional包装，如果不存在则返回空Optional
     */
    Optional<B> findById(Long id);

    /**
     * 根据ID删除实体（逻辑删除）
     *
     * @param id 要删除的实体ID
     */
    void deleteById(Long id);

    /**
     * 批量删除实体（逻辑删除）
     *
     * @param ids 要删除的实体ID列表
     */
    void deleteByIds(List<Long> ids);

    /**
     * 分页查询
     *
     * @param query 查询参数
     * @return 分页数据
     */
    PageInfo<B> findByPage(Q query);

    /**
     * 统计数量
     * @param query 查询参数
     * @return 总数
     */
    long count(Q query);
}