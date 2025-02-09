package com.adi.to_do_app.controller;

import com.adi.to_do_app.Service.JwtService;
import com.adi.to_do_app.Service.UserService;
import com.adi.to_do_app.model.MyUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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


    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;
    @PostMapping("login")
    public String login(@RequestBody MyUser user){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        if(authentication.isAuthenticated())
            return jwtService.generateToken(user.getUsername());
        else return "Login Failed";
    }
}
