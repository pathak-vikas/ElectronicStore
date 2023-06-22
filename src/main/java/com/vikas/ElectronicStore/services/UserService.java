package com.vikas.ElectronicStore.services;

import com.vikas.ElectronicStore.dtos.UserDTO;
import com.vikas.ElectronicStore.entities.User;

import java.util.List;

public interface UserService {

    //create
    UserDTO createUser(UserDTO userDTO);

    //update

    UserDTO updateUser(UserDTO userDTO, String userId);

    //delete
    void deleteUser(String userId);

    //get all users
    List<UserDTO> getAllUsers();

    //get single user by id
    UserDTO getUserById(String userId);

    //get single user by email
    UserDTO getUserByEmail(String email);

    //search user
    List<UserDTO> searchUser(String keyword);

    //other user specific features
}
