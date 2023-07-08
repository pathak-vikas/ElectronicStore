package com.vikas.ElectronicStore.repositories;

import com.vikas.ElectronicStore.entities.Category;
import com.vikas.ElectronicStore.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, String> {

    // iski implementations runtime pe dynamically provide ho jayegi
    //whenever we user autowire annotation

    List<Category> findByTitleContaining(String keywords);
}
