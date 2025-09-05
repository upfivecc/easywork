package org.easywork.auth.infra.repository.converter;

import org.easywork.auth.domain.user.model.AuthUser;
import org.easywork.auth.infra.repository.po.AuthUserPO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author: upfive
 * @version: 1.0.0
 * @date: 2025/9/4 21:59
 */
@Mapper
public interface AuthUserConverter {

     AuthUserConverter INSTANCE = Mappers.getMapper(AuthUserConverter.class);

     AuthUser po2bo(AuthUserPO po);

     List<AuthUser> po2bo(List<AuthUserPO> po);

}
