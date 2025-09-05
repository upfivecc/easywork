package org.easywork.auth.trigger.http.rest.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author: upfive
 * @version: 1.0.0
 * @date: 2025/9/4 09:08
 */
@Data
public class AuthTokenDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = -2844486913521723466L;

    private String token;
    private String refreshToken;
}
