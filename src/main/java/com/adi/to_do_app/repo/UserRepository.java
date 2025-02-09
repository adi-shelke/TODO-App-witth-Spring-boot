package com.adi.to_do_app.repo;

import com.adi.to_do_app.model.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<MyUser, Integer> {
    MyUser findByUsername(String username);
}
