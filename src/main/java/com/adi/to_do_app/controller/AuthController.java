package com.adi.to_do_app.controller;

import com.adi.to_do_app.Service.UserService;
import com.adi.to_do_app.model.MyUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    private UserService userService;
    @PostMapping("register")
    public MyUser register(@RequestBody MyUser user){
        return userService.register(user);
    }
}
