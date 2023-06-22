package com.vikas.ElectronicStore.dtos;

import jakarta.persistence.Column;
import lombok.*;

@Getter
@Setter
@Builder // baar baar new karke object naa banana pade
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private String userId;
    private String name;
    private String email;
    private String password;
    private String gender;
    private String about;
    private String imageNameUrl;
}
