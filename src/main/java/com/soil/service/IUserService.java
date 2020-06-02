package com.soil.service;

import com.soil.common.ServerResponse;
import com.soil.pojo.User;

public interface IUserService {

    // Todo 登陆账号
    ServerResponse<User> login(String username, String password);

    // Todo 注册账号

    // Todo 认证账号

    // Todo 注销账号


}
