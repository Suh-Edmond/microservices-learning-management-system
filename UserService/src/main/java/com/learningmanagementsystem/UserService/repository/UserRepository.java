package com.learningmanagementsystem.UserService.repository;

import com.learningmanagementsystem.UserService.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    Boolean existsByUsername (String userName);
    Boolean existsByEmail(String email);
}
