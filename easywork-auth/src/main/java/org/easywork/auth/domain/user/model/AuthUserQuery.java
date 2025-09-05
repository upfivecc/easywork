package org.easywork.auth.domain.user.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.easywork.common.rest.request.PageQuery;

import java.io.Serial;

/**
 * @author: upfive
 * @version: 1.0.0
 * @date: 2025/9/5 15:33
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AuthUserQuery extends PageQuery {
    @Serial
    private static final long serialVersionUID = -5766401972460091611L;

}
