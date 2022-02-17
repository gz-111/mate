package com.cnsmash.config.login.pojo;

import lombok.Data;

/**
 * @author guanhuan_li
 * #date  2020/9/4 11:35
 */
@Data
public class LoginUserVo {
    private String username;
    private String id;
    private Long userId;
    private String name;
    private RoleType roleType;
    private String token;
}
