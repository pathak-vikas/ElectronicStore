package com.vikas.ElectronicStore.services.Impl;

import com.vikas.ElectronicStore.dtos.CategoryDTO;
import com.vikas.ElectronicStore.dtos.PageableResponse;
import com.vikas.ElectronicStore.entities.Category;
import com.vikas.ElectronicStore.exceptions.ResourceNotFoundException;
import com.vikas.ElectronicStore.helper.Helper;
import com.vikas.ElectronicStore.repositories.CategoryRepository;
import com.vikas.ElectronicStore.repositories.UserRepository;
import com.vikas.ElectronicStore.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryDTO create(CategoryDTO categoryDTO) {
        //dto to entity mapper
        Category category =  modelMapper.map(categoryDTO, Category.class);
       Category savedCategory =  categoryRepository.save(category);

        return modelMapper.map(savedCategory, CategoryDTO.class);
    }

    @Override
    public CategoryDTO update(CategoryDTO categoryDTO, String categoryId) {
        //finding id first : find by id returns optional so we handle else throw exeception
       Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category not found ")) ;

       //update category details
        category.setTitle(categoryDTO.getTitle());
        category.setDescription(categoryDTO.getDescription());
        category.setCoverImage(categoryDTO.getCoverImage());
        //update in database using jparepositiry
       Category updatedCategory =  categoryRepository.save(category);

        return modelMapper.map(updatedCategory, CategoryDTO.class);
    }

    @Override
    public void delete(String categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category not found ")) ;
        categoryRepository.delete(category);
    }

    @Override
    public PageableResponse<CategoryDTO> getAll(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber,pageSize);
        Page<Category> page =  categoryRepository.findAll(pageable);
        PageableResponse pageableResponse =  Helper.getPageableResponse(page, CategoryDTO.class);

        return pageableResponse;
    }

    @Override
    public CategoryDTO get(String categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category not found "));
        return modelMapper.map(category, CategoryDTO.class);
    }
}
