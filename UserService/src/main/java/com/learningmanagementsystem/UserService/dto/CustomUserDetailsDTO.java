package com.learningmanagementsystem.UserService.dto;

import com.learningmanagementsystem.UserService.model.User;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


@Data
public class CustomUserDetailsDTO implements UserDetails {

    private String Id;
    private String username;
    private String name;
    private String email;
    private String telephone;
    private List<GrantedAuthority> authorities;

    public CustomUserDetailsDTO(User user){
            super();
            this.Id = user.getId();
            this.name = user.getName();
            this.username = user.getUsername();
            this.email = user.getEmail();
            this.telephone = user.getTelephone();
            this.authorities = Arrays.stream(user.getRole().split(",")).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.getAuthorities();
    }

    @Override
    public String getPassword() {
        return this.getPassword();
    }

    @Override
    public String getUsername() {
        return getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    public String getId(){
        return this.getId();
    }

    public String getName(){
        return this.getName();
    }

    public String getTelephone(){
        return this.getTelephone();
    }

    public String getEmail(){
        return this.getEmail();
    }
}
