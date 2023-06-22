package com.vikas.ElectronicStore.repositories;

import com.vikas.ElectronicStore.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    // iski implementations runtime pe dynamically provide ho jayegi
    //whenever we user autowire annotation

    //
    Optional<User> findByEmail(String email);
    Optional<User> findByEmailAndPassword(String email, String password);

    List<User> findByNameContaining(String keywords);

}
