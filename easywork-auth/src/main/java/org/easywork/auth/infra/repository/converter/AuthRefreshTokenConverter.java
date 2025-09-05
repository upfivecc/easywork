package org.easywork.auth.infra.repository.converter;

import org.easywork.auth.domain.token.model.AuthRefreshToken;
import org.easywork.auth.infra.repository.po.AuthRefreshTokenPO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author: upfive
 * @version: 1.0.0
 * @date: 2025/9/4 21:59
 */
@Mapper
public interface AuthRefreshTokenConverter {

    AuthRefreshTokenConverter INSTANCE = Mappers.getMapper(AuthRefreshTokenConverter.class);

    AuthRefreshToken po2bo(AuthRefreshTokenPO po);

    AuthRefreshTokenPO bo2po(AuthRefreshToken bo);

}
