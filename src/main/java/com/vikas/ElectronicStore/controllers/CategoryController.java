package com.vikas.ElectronicStore.controllers;

import com.vikas.ElectronicStore.dtos.ApiResponseMessage;
import com.vikas.ElectronicStore.dtos.CategoryDTO;
import com.vikas.ElectronicStore.dtos.PageableResponse;
import com.vikas.ElectronicStore.dtos.UserDTO;
import com.vikas.ElectronicStore.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;
    //create
    @PostMapping
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO){
        //call service to save object
       CategoryDTO  categoryDTO1  = categoryService.create(categoryDTO);
       return new ResponseEntity<>(categoryDTO1, HttpStatus.CREATED);

    }

    //update
    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDTO>  updateCategory(
            @PathVariable("categoryId") String categoryId,
            @Valid @RequestBody CategoryDTO categoryDTO ){
        CategoryDTO categoryDTO1 = categoryService.update(categoryDTO, categoryId);
        return new ResponseEntity<>(categoryDTO1, HttpStatus.OK);
    }

    //delete
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponseMessage> deleteCategory(
            @PathVariable("categoryId") String categoryId
    ){
        categoryService.delete(categoryId);
        ApiResponseMessage responseMessage = ApiResponseMessage.builder()
                .status(HttpStatus.OK)
                .message("Category is deleted sucessfully")
                .success(true)
                .build();

        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }

    //get all
    @GetMapping
    public ResponseEntity<PageableResponse<CategoryDTO>> getAllCategories(
           @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
           //page.size is defined in application.properties as application constant
           //refer chat GPT for how to setup application constants for spring boot app
           @RequestParam(value = "pageSize", defaultValue = "${page.size}", required = false) int pageSize,
           @RequestParam(value = "sortBy", defaultValue = "name", required = false) String sortBy,
           @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
    ){
        PageableResponse<CategoryDTO> pageableResponse =   categoryService.getAll(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(pageableResponse, HttpStatus.OK);
    }

    //get single
    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDTO> getSingle(
            @PathVariable("categoryId") String categoryId
    ){
        CategoryDTO categoryDTO =  categoryService.get(categoryId);
        return new ResponseEntity<>(categoryDTO, HttpStatus.OK);
    }

    @GetMapping("/search/{keywords}")
    public ResponseEntity<List<CategoryDTO>> searchCategory(
            @PathVariable String keywords
    ){
        return new ResponseEntity<>(categoryService.searchCategory(keywords), HttpStatus.OK);
    }
}
