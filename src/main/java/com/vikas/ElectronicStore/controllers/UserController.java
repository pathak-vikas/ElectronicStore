package com.vikas.ElectronicStore.controllers;


import com.vikas.ElectronicStore.dtos.ApiResponseMessage;
import com.vikas.ElectronicStore.dtos.UserDTO;
import com.vikas.ElectronicStore.services.UserService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {


    @Autowired
    private UserService userService;
    //create

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO){
            UserDTO userDTO1 = userService.createUser(userDTO);
            return new ResponseEntity<>(userDTO1, HttpStatus.CREATED);
    }
    //update
    @PutMapping("/{userId}")
    public ResponseEntity<UserDTO> updateUser(
            @PathVariable("userId") String userId,
            @RequestBody UserDTO userDTO
    ){
        UserDTO updatedUserDto = userService.updateUser(userDTO, userId);
        return new ResponseEntity<>(updatedUserDto, HttpStatus.OK);
    }

    //delete
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponseMessage> deleteUser(
            @PathVariable("userId") String userId
    ){
       userService.deleteUser(userId);
       ApiResponseMessage message = ApiResponseMessage.builder()
               .message("User deleted Successfully")
               .success(true)
               .status(HttpStatus.OK)
               .build();
       return new ResponseEntity<>(message, HttpStatus.OK);

    }

    //get all
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers(){
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    //get single user
    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUser(
            @PathVariable String userId
    ){
        return new ResponseEntity<>(userService.getUserById(userId), HttpStatus.OK);
    }

    //getUser by email
    @GetMapping("/email/{emailId}")
    public ResponseEntity<UserDTO> getUserByEmail(
            @PathVariable String emailId
    ){
        return new ResponseEntity<>(userService.getUserByEmail(emailId), HttpStatus.OK);
    }

    //search user email
    @GetMapping("/search/{keywords}")
    public ResponseEntity<List<UserDTO>> searchUser(
            @PathVariable String keywords
    ){
        return new ResponseEntity<>(userService.searchUser(keywords), HttpStatus.OK);
    }

}
