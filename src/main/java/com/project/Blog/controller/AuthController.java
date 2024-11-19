package com.project.Blog.controller;

import com.project.Blog.config.JwtTokenHelper;
import com.project.Blog.dto.UserDto;
import com.project.Blog.dto.UserLoginRequest;
import com.project.Blog.dto.UserLoginResponse;
import com.project.Blog.entity.User;
import com.project.Blog.repository.UserRepository;
import com.project.Blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.security.authentication.AuthenticationManager;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenHelper jwtTokenHelper;


    @PostMapping("/user/create")
    public UserDto createUser(@RequestBody UserDto userDTO) throws IOException {
        return userService.registerNewUser(userDTO);
    }

    @GetMapping("/users")
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/user/{id}")
    public UserDto getUser(@PathVariable long id) {
        return userService.getUserById(id);
    }

    @PatchMapping("/user/{id}")
    public UserDto updateUser(@PathVariable long id, @RequestBody UserDto userDTO) {
        return userService.updateUserById(id,userDTO);

    }

    @DeleteMapping("user/{id}")
    public void deleteUser(@PathVariable long id) {
        userService.deleteUserById(id);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserLoginRequest request) {

        Optional<User> theUser = userRepository.findByEmail(request.getEmail());
        UserLoginResponse response = new UserLoginResponse();
        if (theUser.isPresent()) {
            User user = theUser.get();

            try {
                Authentication authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
                );
                if (authentication.isAuthenticated()) {
                    response.setToken(jwtTokenHelper.generateToken(user.getEmail(), "user"));
                    response.setMessage("Login Successful");
                    response.setUserId(user.getId());
                    response.setFirstName(user.getFirstName());
                    response.setLastName(user.getLastName());
                    response.setMobileNumber(user.getMobileNumber());
                    response.setEmail(user.getEmail());
                    response.setRole(user.getRole());
//                    response.setTokenExpiryTime(jwtTokenHelper.getExpirationSecondsFromToken(jwtTokenHelper.generateToken(user.getEmail(),"user")));
//                    response.setMediaPlayerDownloaded(user.isMediaPlayerDownloaded());
//                    response.setCanDownloadMediaPlayer(user.isCanDownloadMediaPlayer());
//                    logInfo.info("Login successful "+ user.getEmail());
                    return new ResponseEntity<>(response, HttpStatus.OK);
                } else {
//                    logInfo.error("Invalid email or Password!");
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                            .body(Map.of("status", "error", "message", "Invalid email or password. Please try again."));
                }


            }
            catch (AuthenticationException e) {
//                logInfo.error("Invalid email or Password!");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("status", "error", "message", "Invalid email or password. Please try again."));
            }

        }
        else {
//            logInfo.error("User not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("status", "error", "message", "User not found"));
        }

    }







}
