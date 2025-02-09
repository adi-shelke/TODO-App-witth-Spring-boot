package com.adi.to_do_app.Service;

import com.adi.to_do_app.model.MyUser;
import com.adi.to_do_app.model.UserPrincipal;
import com.adi.to_do_app.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MyUser user = userRepository.findByUsername(username);
        if(user==null){
            throw  new UsernameNotFoundException("User 404");
        }
        return  new UserPrincipal(user);
    }
}
