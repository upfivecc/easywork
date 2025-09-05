package org.easywork.auth.infra.repository.po;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Date;

/**
 * @author: upfive
 * @version: 1.0.0
 * @date: 2025/9/4 14:45
 */
@Builder
@Data
@Entity
@Table(name = "auth_refresh_token")
@NoArgsConstructor
@AllArgsConstructor
public class AuthRefreshTokenPO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String clientId;
    private Long userId;
    private String refreshToken;
    private Date expireAt;
    private String extra;
    private String createBy;
    private Instant createTime;
    private String updateBy;
    private Instant updateTime;
    private String isDeleted;
}
