package com.vikas.ElectronicStore.controllers;


import com.vikas.ElectronicStore.dtos.ApiResponseMessage;
import com.vikas.ElectronicStore.dtos.ImageResponse;
import com.vikas.ElectronicStore.dtos.PageableResponse;
import com.vikas.ElectronicStore.dtos.UserDTO;
import com.vikas.ElectronicStore.services.FileService;
import com.vikas.ElectronicStore.services.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.Getter;
//import lombok.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

//    @Value("${page.size}")
//    private String defaultPageSize;
    @Autowired
    private UserService userService;
    //create
    @Autowired
    private FileService fileService;

    @Value("${user.profile.image.path}")
    private String imageUploadPath;

    private Logger logger = LoggerFactory.getLogger(UserController.class);
    @PostMapping
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDTO){
            UserDTO userDTO1 = userService.createUser(userDTO);
            return new ResponseEntity<>(userDTO1, HttpStatus.CREATED);
    }
    //update
    @PutMapping("/{userId}")
    public ResponseEntity<UserDTO> updateUser(
            @PathVariable("userId") String userId,
            @Valid @RequestBody UserDTO userDTO
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
    public ResponseEntity<PageableResponse<UserDTO>> getAllUsers(
            //bydefault required = true
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            //page.size is defined in application.properties as application constant
            //refer chat GPT for how to setup application constants for spring boot app
            @RequestParam(value = "pageSize", defaultValue = "${page.size}", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "name", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
    ){
        return new ResponseEntity<>(userService.getAllUsers(pageNumber, pageSize, sortBy, sortDir), HttpStatus.OK);
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

    //search user name keywords
    @GetMapping("/search/{keywords}")
    public ResponseEntity<List<UserDTO>> searchUser(
            @PathVariable String keywords
    ){
        return new ResponseEntity<>(userService.searchUser(keywords), HttpStatus.OK);
    }

    //upload user image
    @PostMapping("/image/{userId}")
    public ResponseEntity<ImageResponse>  uploadUserImage(@RequestParam("userImage") MultipartFile image, @PathVariable() String userId ) throws IOException {

        String imageName = fileService.uploadFile(image, imageUploadPath);

       UserDTO user = userService.getUserById(userId);
       user.setImageNameUrl(imageName);
       UserDTO userDto = userService.updateUser(user, userId);

        ImageResponse imageResponse = ImageResponse.builder()
                                        .imageName(imageName)
                                        .success(true)
                                        .message("Image is upladed sucesfully")
                                        .status(HttpStatus.CREATED)
                                        .build();
        return new ResponseEntity<>(imageResponse,HttpStatus.CREATED);

    }


    //serve user image
    @GetMapping(value = "/image/{userId}")
    public void serveUserImage(@PathVariable String userId, HttpServletResponse response) throws IOException {

        UserDTO user = userService.getUserById(userId);
        logger.info("USer Image name : {} ",user.getImageNameUrl());
        InputStream resource = fileService.getResource(imageUploadPath, user.getImageNameUrl());

        response.setContentType(MediaType.IMAGE_JPEG_VALUE);

        StreamUtils.copy(resource, response.getOutputStream());

    }

}
