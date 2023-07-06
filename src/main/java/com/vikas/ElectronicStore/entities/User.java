package com.vikas.ElectronicStore.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "users") //inorder to customize entity tablename by defaullt class name is tablename
public class User {

    @Id // to make field primary key
    //@GeneratedValue(strategy = GenerationType.IDENTITY) incase you want this id to be autoincrement
    private String userId;

    @Column(name = "user_name")
    private String name;
    @Column(name = "user_email", unique = true)
    private String email;

    @Column(name = "user_password", length = 500)
    private String password;
    private String gender;

    @Column(length = 1000)
    private String about;

    @Column(name = "user_profile_image")
    private String imageNameUrl;

}
