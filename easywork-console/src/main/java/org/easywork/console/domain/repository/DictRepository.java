package org.easywork.console.domain.repository;

import org.easywork.console.domain.model.Dict;
import org.easywork.console.domain.model.dto.DictQuery;
import org.easywork.console.domain.repository.base.BaseRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author: upfive
 * @version: 1.0.0
 * @date: 2025/9/10 09:09
 */
public interface DictRepository extends BaseRepository<Dict, DictQuery> {

    /**
     * 根据字典编码查找字典
     */
    Optional<Dict> findByCode(String code);

    /**
     * 查找所有启用的字典
     */
    List<Dict> findAllEnabled();

    /**
     * 检查字典编码是否存在
     */
    boolean existsByCode(String code);

}
