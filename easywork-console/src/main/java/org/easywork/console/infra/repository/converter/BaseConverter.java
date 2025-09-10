package org.easywork.console.infra.repository.converter;

/**
 * @author: upfive
 * @version: 1.0.0
 * @date: 2025/9/10 17:30
 */
public interface BaseConverter<P, B> {
    /**
     * 持久化对象转领域对象
     */
    B toDomain(P entity);
    /**
     * 领域对象转持久化对象
     */
    P toRepository(B domain);
}
