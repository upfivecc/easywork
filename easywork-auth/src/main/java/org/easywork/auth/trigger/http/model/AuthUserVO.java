package org.easywork.auth.trigger.http.model;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;

/**
 * @author: upfive
 * @version: 1.0.0
 * @date: 2025/9/5 15:27
 */
@Data
public class AuthUserVO implements Serializable {
    @Serial
    private static final long serialVersionUID = 9024674668180554761L;

    private Long id;
    private String username;
    private String password;
    private String phone;
    private String email;
    private String salt;
    private String status;
    private String avatar;
    private String createBy;
    private Instant createTime;
    private String updateBy;
    private Instant updateTime;
    private String isDeleted;

}
