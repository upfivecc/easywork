package org.easywork.auth.domain.client.model;

import lombok.Data;

import java.time.Instant;
import java.util.Date;

/**
 * @author: fiveupup
 * @version: 1.0.0
 * @date: 2025/9/4 21:43
 */
@Data
public class AuthClient {
    private Long id;
    private String clientId;
    private String clientSecret;
    private String redirectUri;
    private String grantTypes;
    private String scope;
    private String createBy;
    private Instant createTime;
    private String updateBy;
    private Instant updateTime;
    private String isDeleted;
}
