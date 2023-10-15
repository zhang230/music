package com.music.service;

import com.music.dao.User;
import com.music.mapper.LoginMapper;

public class LoginService {

    private LoginMapper loginMapper;
    public LoginService(){
        loginMapper = new LoginMapper();
    }

    public User Login(User user) {
       return loginMapper.Login(user);
    }

    public Integer Register(User user) {
        Integer repeat = loginMapper.isRepeatUserName(user);
        if(repeat == 1) {
            return 0;
        } else return loginMapper.Register(user);
    }
}
