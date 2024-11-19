package com.project.Blog.config;

//import com.project.Blog.entity.Role;
import com.project.Blog.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

public class UserUserDetail implements UserDetails {
    private String userName;
    private String password;
    private String role;


    public UserUserDetail(User user) {
        userName = user.getEmail();
        password = user.getPassword();
        role = user.getRole();
    }

    public UserUserDetail(Object o) {
    }

    //    public Collection<? extends GrantedAuthority> getAuthorities() {
    //        return null;
    //    }


        public Collection<? extends GrantedAuthority> getAuthorities() {
            return Collections.singletonList(new SimpleGrantedAuthority(this.role));
    }


//    public Collection<? extends GrantedAuthority> getAuthorities() {
////        List<SimpleGrantedAuthority> authorities = this.role.stream()
////                .map((role) -> new SimpleGrantedAuthority(role.getRole())).collect(Collectors.toList());
////        return authorities;
//        return this.role.stream()
//                .map((role1) -> new SimpleGrantedAuthority(role1.getRole())).collect(Collectors.toList());
//    }


    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

