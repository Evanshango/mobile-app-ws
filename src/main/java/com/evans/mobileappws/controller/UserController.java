package com.evans.mobileappws.controller;

import com.evans.mobileappws.dto.AddressDto;
import com.evans.mobileappws.dto.UserDto;
import com.evans.mobileappws.model.request.UserDetails;
import com.evans.mobileappws.model.response.*;
import com.evans.mobileappws.service.AddressService;
import com.evans.mobileappws.service.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.BeanUtils;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@AllArgsConstructor
@RestController
@RequestMapping("/api")
public class UserController {
    private final UserService userService;
    private final AddressService addressService;

    @GetMapping(path = "/user/{userId}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public UserRest getUser(@PathVariable String userId) {
        UserRest returnValue = new UserRest();
        UserDto userDto = userService.getUserByUserId(userId);
        BeanUtils.copyProperties(userDto, returnValue);
        return returnValue;
    }

    @PostMapping(path = "/create-user",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public UserRest createUser(@RequestBody UserDetails userDetails) {
        ModelMapper modelMapper = new ModelMapper();
        UserDto userDto = modelMapper.map(userDetails, UserDto.class);

        UserDto createdUser = userService.createUser(userDto);
        return modelMapper.map(createdUser, UserRest.class);
    }

    @PutMapping(path = "/update-user/{userId}",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public UserRest updateUser(@RequestBody UserDetails userDetails, @PathVariable String userId) {
        UserRest returnValue = new UserRest();
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userDetails, userDto);

        UserDto updatedUser = userService.updateUser(userId, userDto);
        BeanUtils.copyProperties(updatedUser, returnValue);

        return returnValue;
    }

    @DeleteMapping(path = "/delete-user/{userId}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public OperationStatus deleteUser(@PathVariable String userId) {
        OperationStatus returnValue = new OperationStatus();
        returnValue.setOperationName(RequestOperationName.DELETE.name());

        userService.deleteUser(userId);

        returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
        return returnValue;
    }

    @GetMapping(path = "/users",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public List<UserRest> getUsers(@RequestParam(value = "page", defaultValue = "0") int page,
                                   @RequestParam(value = "limit", defaultValue = "25") int limit) {
        List<UserRest> returnValue = new ArrayList<>();
        List<UserDto> users = userService.getUsers(page, limit);
        for (UserDto userDto : users) {
            UserRest userRest = new UserRest();
            BeanUtils.copyProperties(userDto, userRest);
            returnValue.add(userRest);
        }
        return returnValue;
    }

    @GetMapping(path = "user/{userId}/addresses",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, "application/hal+json"})
    public CollectionModel<AddressRest> getUserAddresses(@PathVariable String userId) {
        List<AddressRest> returnValue = new ArrayList<>();

        List<AddressDto> addressDtos = addressService.getAddresses(userId);
        if (addressDtos != null && !addressDtos.isEmpty()) {
            Type listType = new TypeToken<List<AddressRest>>() {
            }.getType();
            returnValue = new ModelMapper().map(addressDtos, listType);

            for (AddressRest addressRest : returnValue) {
                Link addressLink = linkTo(methodOn(UserController.class)
                        .getUserAddress(userId, addressRest.getAddressId())).withSelfRel();
                addressRest.add(addressLink);

                Link userLink = linkTo(methodOn(UserController.class).getUser(userId)).withRel("user");
                addressRest.add(userLink);
            }
        }
        return new CollectionModel<>(returnValue);
    }

    @GetMapping(path = "/user/{userId}/addresses/{addressId}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, "application/hal+json"})
    public AddressRest getUserAddress(@PathVariable String userId, @PathVariable String addressId) {
        AddressDto addressDto = addressService.getAddress(addressId);
        ModelMapper modelMapper = new ModelMapper();

        Link addressLink = linkTo(methodOn(UserController.class).getUserAddress(userId, addressId)).withSelfRel();
        Link addresses = linkTo(methodOn(UserController.class).getUserAddresses(userId)).withRel("addresses");

        AddressRest addressRest = modelMapper.map(addressDto, AddressRest.class);
        addressRest.add(addressLink);
        addressRest.add(addresses);

        return addressRest;
    }
}
