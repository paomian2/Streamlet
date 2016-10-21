package com.streamlet.module.entity.response;

import com.streamlet.module.entity.base.BaseResponse;
import com.streamlet.module.entity.bean.User;

/**
 * Created by streamlet2 on 2016/10/20.
 *
 * @Description 登录网络返回的实体类
 */
public class LoginResponse extends BaseResponse{

    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LoginResponse(){}

    public LoginResponse(User user) {
        this.user = user;
    }
}
