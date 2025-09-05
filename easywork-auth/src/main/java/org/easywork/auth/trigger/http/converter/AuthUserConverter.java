package org.easywork.auth.trigger.http.converter;

import org.easywork.auth.domain.user.model.AuthUser;
import org.easywork.auth.trigger.http.model.AuthUserVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author: upfive
 * @version: 1.0.0
 * @date: 2025/9/5 15:27
 */
@Mapper
public interface AuthUserConverter {

    AuthUserConverter INSTANCE = Mappers.getMapper(AuthUserConverter.class);

    AuthUserVO authUser2AuthUserVO(AuthUser authUser);

    List<AuthUserVO> authUser2AuthUserVO(List<AuthUser> authUser);


}
