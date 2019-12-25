package com.evans.mobileappws.controller;

import com.evans.mobileappws.dto.UserDto;
import com.evans.mobileappws.model.request.UserDetails;
import com.evans.mobileappws.model.response.UserRest;
import com.evans.mobileappws.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/user")
    public String getUser() {
        return "get user was called";
    }

    @PostMapping("/create-user")
    public UserRest createUser(@RequestBody UserDetails userDetails) {
        UserRest userRest = new UserRest();

        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userDetails, userDto);

        UserDto createdUser = userService.createUser(userDto);
        BeanUtils.copyProperties(createdUser, userRest);

        return userRest;
    }

    @PutMapping("/update-user")
    public String updateUser() {
        return "update user was called";
    }

    @DeleteMapping("/delete-user")
    public String deleteUser() {
        return "delete user was called";
    }
}
