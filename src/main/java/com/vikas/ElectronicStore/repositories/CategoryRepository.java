package com.vikas.ElectronicStore.repositories;

import com.vikas.ElectronicStore.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, String> {

}
