package com.cnsmash.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cnsmash.exception.CodeException;
import com.cnsmash.exception.ErrorCode;
import com.cnsmash.mapper.AccountMapper;
import com.cnsmash.mapper.UserMapper;
import com.cnsmash.pojo.entity.Account;
import com.cnsmash.pojo.entity.User;
import com.cnsmash.pojo.ro.RegisterUserRo;
import com.cnsmash.service.AccountService;
import com.cnsmash.util.JsonUtil;
import com.fasterxml.jackson.databind.util.BeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author guanhuan_li
 */
@Slf4j
@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    AccountMapper accountMapper;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserMapper userMapper;

    @Override
    public Account login(String username){
        return getAccountByAccount(username);
    }

    @Override
    public User register(RegisterUserRo ro) {

        if (getAccountByAccount(ro.getAccount()) != null) {
            throw new CodeException(ErrorCode.ACCOUNT_EXIT, "账号已存在");
        }
        if (getUserByNickName(ro.getNickName()) != null) {
            throw new CodeException(ErrorCode.NICKNAME_EXIT, "昵称已存在");
        }

        Account account = new Account();
        BeanUtils.copyProperties(ro, account);

        Timestamp now = Timestamp.valueOf(LocalDateTime.now());
        account.setPassword(passwordEncoder.encode(ro.getPassword().trim()));
        account.setUpdateTime(now);
        account.setCreateTime(now);
        accountMapper.insert(account);

        User user = new User();
        BeanUtils.copyProperties(ro, user);
        user.setAccountId(account.getId());
        user.setCode("");
        user.setTagJson(JsonUtil.toJson(ro.getTags()));
        user.setUpdateTime(now);
        user.setCreateTime(now);
        userMapper.insert(user);

        return user;
    }


    private Account getAccountByAccount(String username) {
        QueryWrapper<Account> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("account", username);
        return accountMapper.selectOne(queryWrapper);
    }

    private User getUserByNickName(String nickName) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("nick_name", nickName);
        return userMapper.selectOne(queryWrapper);
    }

    private boolean exit(String username, String nickName) {
        QueryWrapper<Account> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("nick_name", nickName).or().eq("account", username);
        return accountMapper.selectCount(queryWrapper) > 0;
    }
}
