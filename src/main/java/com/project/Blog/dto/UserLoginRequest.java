package com.project.Blog.dto;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserLoginRequest {

    private String email;
    private String password;


}
