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
 * @date: 2025/9/4 10:02
 */
@Builder
@Data
@Entity
@Table(name = "auth_user")
@NoArgsConstructor
@AllArgsConstructor
public class AuthUserPO {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
