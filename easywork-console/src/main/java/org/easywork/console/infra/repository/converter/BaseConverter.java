package org.easywork.console.infra.repository.converter;

/**
 * @author: upfive
 * @version: 1.0.0
 * @date: 2025/9/10 17:06
 */
public interface BaseConverter<E, D> {
    D toDomain(E entity);
    E toRepository(D domain);
}
