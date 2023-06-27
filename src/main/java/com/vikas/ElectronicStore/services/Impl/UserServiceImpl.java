package com.vikas.ElectronicStore.services.Impl;

import com.vikas.ElectronicStore.dtos.PageableResponse;
import com.vikas.ElectronicStore.dtos.UserDTO;
import com.vikas.ElectronicStore.entities.User;
import com.vikas.ElectronicStore.exceptions.ResourceNotFoundException;
import com.vikas.ElectronicStore.helper.Helper;
import com.vikas.ElectronicStore.repositories.UserRepository;
import com.vikas.ElectronicStore.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;
    @Override
    public UserDTO createUser(UserDTO userDTO) {
        // generate unique id in string format
        String userId = UUID.randomUUID().toString();
        userDTO.setUserId(userId);

        //dto -> entity
        User user = dtoToEntity(userDTO);
        User savedUser = userRepository.save(user);
        //entity -> dto
        UserDTO newDto = entityToDto(savedUser);
        return newDto;
    }

    @Override
    public UserDTO updateUser(UserDTO userDTO, String userId) {

        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found with given id"));
        user.setName(userDTO.getName());
        //email update if requirement is there
        user.setAbout(userDTO.getAbout());
        user.setGender(userDTO.getGender());
        user.setPassword(userDTO.getImageNameUrl());
        user.setImageNameUrl(userDTO.getImageNameUrl());

        User updatedUser = userRepository.save(user);
        UserDTO updatedUserDTO = entityToDto(updatedUser);
        return updatedUserDTO;
    }

    @Override
    public void deleteUser(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found with given id"));
        //delete user
        userRepository.delete(user);
    }

    @Override
    public PageableResponse<UserDTO> getAllUsers(int pageNumber, int pageSize, String sortBy, String sortDir) {

        Sort sort = (sortDir.equalsIgnoreCase( "desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        //pageNumber default starts from 0
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<User> page = userRepository.findAll(pageable);
        PageableResponse<UserDTO> response = Helper.getPageableResponse(page, UserDTO.class);
        return response;
    }

    @Override
    public UserDTO getUserById(String userId) {
        User fetchedUser = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found with given id"));
        UserDTO fetchedUserDTO = entityToDto(fetchedUser);
        return fetchedUserDTO;
    }

    @Override
    public UserDTO getUserByEmail(String email) {
        User fetchedUser = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found with given Email"));
        UserDTO fetchedUserDTO = entityToDto(fetchedUser);
        return fetchedUserDTO;
    }

    @Override
    public List<UserDTO> searchUser(String keyword) {
        List<User> users = userRepository.findByNameContaining(keyword);
        List<UserDTO> dtoList = users.stream().map(user -> entityToDto(user)).collect(Collectors.toList());
        return dtoList;
    }


    private UserDTO entityToDto(User savedUser) {
//      UserDTO userDTO =  UserDTO.builder()
//                .userId(savedUser.getUserId())
//                .name(savedUser.getName())
//                .email(savedUser.getEmail())
//                .password(savedUser.getPassword())
//                .about(savedUser.getAbout())
//                .gender(savedUser.getGender())
//                .imageNameUrl(savedUser.getImageNameUrl())
//                .build();
//
//      return userDTO;
        return modelMapper.map(savedUser, UserDTO.class);
    }

    private User dtoToEntity(UserDTO userDTO) {
//       User user =  User.builder()
//                .userId(userDTO.getUserId())
//                .name(userDTO.getName())
//                .email(userDTO.getEmail())
//                .password(userDTO.getPassword())
//                .about(userDTO.getAbout())
//                .gender(userDTO.getGender())
//                .imageNameUrl(userDTO.getImageNameUrl()).build();
//
//        return user;
        return modelMapper.map(userDTO, User.class);
    }
}
