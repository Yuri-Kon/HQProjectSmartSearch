package bighomework.web.service.impl;

import bighomework.web.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import bighomework.web.service.LoginService;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public int Login(String username, String password, String usertype) {
        return userMapper.Login(username, password, usertype);
    }




}
