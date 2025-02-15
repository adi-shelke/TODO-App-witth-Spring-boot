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
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
//@CrossOrigin(origins = "*")
@RequestMapping("/auth/")
public class AuthController {

    @Autowired
    private UserService userService;


    @PostMapping("register")
    public ResponseEntity<Map<String, Object>> register(@RequestBody MyUser user){
            Map<String, Object> response = new HashMap<>();
            try{
                MyUser newUser = userService.register(user);
                response.put("message","User registered successfully");
                response.put("user",newUser);
                return ResponseEntity.ok(response);
            }catch (IllegalArgumentException e){
                response.put("message",e.getMessage());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
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
            response.put("message", "Invalid credentials");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(existingUser.getUsername(), user.getPassword()));
        if(authentication.isAuthenticated()){
            String token = jwtService.generateToken(existingUser.getUsername());
            response.put("authtoken",token);
            return ResponseEntity.ok(response);
        }
        else{
            response.put("message","Invalid credentials");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }
}
