package com.vikas.ElectronicStore.dtos;

import com.vikas.ElectronicStore.validate.ImageNameValid;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder // baar baar new karke object naa banana pade
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private String userId;
    @Size(min = 3, max = 25, message = "Invalid Name")
    private String name;

  //  @Email(message = "Invalid User Email!!") -- not able to handle inccorrect email patterns
    //  like if .com is missing it will save that email
    //inorder to avoid that we use regex for correct email patterns
    //using pattern annotations
    @Pattern(regexp = "^[A-Za-z][A-Za-z0-9._]+@([A-Za-z]+\\.)+[A-Za-z]{2,6}$", message = "Invalid User Email")
    @NotBlank(message = "Email is Required")
    private String email;
    @NotBlank(message = "Password is Required")
    private String password;

    @Size(min = 4, max = 6, message = "Invalid Gender!!")
    private String gender;
    @NotBlank(message = "Write something about yourself!!")
    private String about;

    //@Pattern
    //@Custom Validator
    //@ImageNameValid()
    private String imageNameUrl;
}
