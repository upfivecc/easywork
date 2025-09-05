package org.easywork.auth.infra.repository.converter;

import org.easywork.auth.domain.client.model.AuthClient;
import org.easywork.auth.infra.repository.po.AuthClientPO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author: upfive
 * @version: 1.0.0
 * @date: 2025/9/4 21:59
 */
@Mapper
public interface AuthClientConverter {

    AuthClientConverter INSTANCE = Mappers.getMapper(AuthClientConverter.class);

    AuthClient po2bo(AuthClientPO po);

}
