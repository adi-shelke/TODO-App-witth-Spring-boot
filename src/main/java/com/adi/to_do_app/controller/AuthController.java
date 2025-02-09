package com.adi.to_do_app.controller;

import com.adi.to_do_app.Service.JwtService;
import com.adi.to_do_app.Service.UserService;
import com.adi.to_do_app.model.MyUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
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
    public ResponseEntity<Map<String, Object>> login(@RequestBody MyUser user){

        Map<String,Object> response = new HashMap<>();

        MyUser existingUser = userService.findByEmail(user.getEmail());
        if (existingUser == null || !new BCryptPasswordEncoder(12).matches(user.getPassword(), existingUser.getPassword())) {
            response.put("success", false);
            response.put("message", "Invalid credentials");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }


        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(existingUser.getUsername(), user.getPassword()));
        if(authentication.isAuthenticated()){
            String token = jwtService.generateToken(existingUser.getUsername());
            response.put("success",true);
            response.put("authtoken",token);
            return ResponseEntity.ok(response);
        }
        else{
            response.put("success",false);
            response.put("message","Login Failed");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }
}
