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
@Table(name = "auth_client")
@NoArgsConstructor
@AllArgsConstructor
public class AuthClientPO {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
