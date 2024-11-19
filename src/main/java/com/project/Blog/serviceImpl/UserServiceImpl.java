package com.project.Blog.serviceImpl;

import com.project.Blog.dto.UserDto;
import com.project.Blog.entity.User;
import com.project.Blog.repository.UserRepository;
import com.project.Blog.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDto registerNewUser(UserDto userDto) throws IOException {

        User user = this.modelMapper.map(userDto, User.class);
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        user.setCreatedDate(LocalDateTime.now());
        user.setRole("user");
        User newUser = this.userRepository.save(user);
        UserDto response = this.modelMapper.map(newUser, UserDto.class);
        return response;
    }

    @Override
    public List<UserDto> getAllUsers(){
        List<User> users = this.userRepository.findAll();
        List<UserDto> userDtos = new ArrayList<>();
        for (User user : users) {
            UserDto userDto = this.modelMapper.map(user, UserDto.class);
            userDtos.add(userDto);
        }
        return userDtos;

    }

    @Override
    public UserDto getUserById(long id) {
        Optional<User> user = userRepository.findById(id);
        UserDto userDto = this.modelMapper.map(user, UserDto.class);
        return userDto;
    }

    @Override
    public void  deleteUserById(long id){
        userRepository.deleteById(id);
    }

    @Override
    public UserDto updateUserById(long id, UserDto userDTO){
        Optional<User> user = userRepository.findById(id);
        User newUser = this.modelMapper.map(userDTO, User.class);
        newUser.setId(id);

        if(newUser.getAddress() != null){
            newUser.setAddress(userDTO.getAddress());
        }
        else if (userDTO.getMobileNumber() != null){
            newUser.setMobileNumber(userDTO.getMobileNumber());
        }
        else if (userDTO.getEmail() != null){
            newUser.setEmail(userDTO.getEmail());
        }
        else if (userDTO.getFirstName() != null){
            newUser.setFirstName(userDTO.getFirstName());
        } else if ( userDTO.getLastName() != null) {
            newUser.setLastName(userDTO.getLastName());

        }
        newUser.setCreatedDate(LocalDateTime.now());
        User savedUser = this.userRepository.save(newUser);
        UserDto response = this.modelMapper.map(savedUser, UserDto.class);
        return response;

    }
}
