package org.easywork.auth.domain.user.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.io.Serializable;
import java.security.Principal;
import java.time.Instant;
import java.util.Set;

/**
 * @author: upfive
 * @version: 1.0.0
 * @date: 2025/9/4 09:49
 */
@Builder
@Data
public class AuthUser implements UserDetails, Principal, Serializable {
    @Serial
    private static final long serialVersionUID = 2336889308886928519L;

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

    private Set<GrantedAuthority> authorities;


    @Override
    public String getName() {
        return this.username;
    }
}
