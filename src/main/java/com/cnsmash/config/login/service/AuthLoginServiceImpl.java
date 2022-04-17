package com.cnsmash.config.login.service;

import com.cnsmash.config.login.pojo.*;
import com.cnsmash.pojo.LoginAuth;
import com.cnsmash.pojo.entity.Account;
import com.cnsmash.pojo.entity.User;
import com.cnsmash.pojo.vo.UserInfo;
import com.cnsmash.service.AccountService;
import com.cnsmash.service.UserService;
import com.cnsmash.util.JsonUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * @author guanhuan_li
 * #date  2020/12/2 15:22
 */
@Component
public class AuthLoginServiceImpl implements LoginUserService {

    @Autowired
    AccountService accountService;

    @Autowired
    UserService userService;

    @Override
    public LoginUserVo getLoginUserVo(LoginUser loginUser) {
        LoginUserVo vo = new LoginUserVo();
        BeanUtils.copyProperties(loginUser, vo);
        return vo;
    }

    @Override
    public LoginType getLoginType() {
        return LoginType.auth;
    }

    @Override
    public PasswordEncoder getPasswordEncoder() {
        // 多身份登录不需要加密
        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence rawPassword) {
                return (String) rawPassword;
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                return rawPassword.equals(encodedPassword);
            }
        };
    }

    @Override
    public LoginUser loadUser(LoginUserRo ro) throws UsernameNotFoundException {

        // 用户id作为用户名
        String userIdStr = ro.getUsername();
        if (StringUtils.isBlank(userIdStr)) {
            throw new UsernameNotFoundException("用户id错误或者不能为空！");
        }

        long userId;
        try {
            userId = Long.parseLong(userIdStr);
        } catch (NumberFormatException e) {
            throw new UsernameNotFoundException("用户id错误或者不能为空！");
        }

        HttpSession httpSession = ro.getDetails().getHttpSession();
        String authJson = (String) httpSession.getAttribute(AccountService.LOGIN_AUTH_KEY);

        if (StringUtils.isBlank(authJson)) {
            throw new BadCredentialsException("登录失效请重新登录！");
        }
        LoginAuth loginAuth = JsonUtil.parseJson(authJson, new TypeReference<LoginAuth>() {
        });

        User user = userService.getById(userId);
        if (user == null) {
            // 账号不存在
            throw new BadCredentialsException("账号不存在！");
        }
        if (!user.getAccountId().equals(loginAuth.getAccountId())) {
            throw new BadCredentialsException("身份不存在！");
        }

        Account account = accountService.get(user.getAccountId());
        LoginUser loginUser = new LoginUser();
        BeanUtils.copyProperties(account, loginUser);
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_ACCOUNT"));
        loginUser.setId(account.getId());
        loginUser.setAuthorities(authorities);
        loginUser.setRoleType(RoleType.user);
        loginUser.setLoginType(getLoginType());
        loginUser.setAccountNonLocked(true);
        loginUser.setCredentialsNonExpired(true);
        loginUser.setAccountNonExpired(true);
        loginUser.setEnabled(true);
        loginUser.setId(account.getId());
        loginUser.setUserId(user.getId());
        // 登录凭证作为密码，和传过来的进行等值比较
        loginUser.setPassword(loginAuth.getAuth());
        loginUser.setUsername(account.getAccount());
        return loginUser;
    }
}