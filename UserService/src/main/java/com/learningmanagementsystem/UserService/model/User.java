package com.learningmanagementsystem.UserService.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@Entity
public class User {

    @Id
    private String Id;
    @Column(unique = true)
    private String username;
    private String name;
    @Column(unique = true)
    private String email;
    @Column(unique = true)
    private String telephone;
    private String password;
    private  String role;
    @Transient
    private String confirmPassword;


}
