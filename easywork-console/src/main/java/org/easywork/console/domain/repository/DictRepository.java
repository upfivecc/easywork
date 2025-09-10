package org.easywork.console.domain.repository;

import cn.hutool.core.lang.Dict;

import java.util.List;
import java.util.Optional;

/**
 * @author: upfive
 * @version: 1.0.0
 * @date: 2025/9/10 09:09
 */
public interface DictRepository {

    /**
     * 保存字典
     */
    Dict save(Dict dict);

    /**
     * 根据ID查找字典
     */
    Optional<Dict> findById(Long id);

    /**
     * 根据字典编码查找字典
     */
    Optional<Dict> findByCode(String code);

    /**
     * 分页查询字典
     */
    List<Dict> findByPage(int page, int size, String keyword);

    /**
     * 统计字典数量
     */
    long count(String keyword);

    /**
     * 查找所有启用的字典
     */
    List<Dict> findAllEnabled();

    /**
     * 检查字典编码是否存在
     */
    boolean existsByCode(String code);

    /**
     * 删除字典
     */
    void deleteById(Long id);

    /**
     * 批量删除字典
     */
    void deleteByIds(List<Long> ids);
}
