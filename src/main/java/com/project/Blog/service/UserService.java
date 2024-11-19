package com.project.Blog.service;

import com.project.Blog.dto.UserDto;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public interface UserService {

      public UserDto registerNewUser(UserDto userDto) throws IOException;
//    public UserDto loginUser(UserDto userDto) throws IOException;
//    public UserDto updateUser(UserDto userDto);
      public void  deleteUserById(long id);
      public List<UserDto> getAllUsers();
      public UserDto getUserById(long id) ;
      public UserDto updateUserById(long id, UserDto userDTO);
//    public UserDto getUserByEmail(String email) ;

}
