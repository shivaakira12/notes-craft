package com.notescraft.service;


import com.notescraft.dto.CategoryDTO;
import com.notescraft.dto.CategoryResponse;
import com.notescraft.entity.Category;
import com.notescraft.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;


public interface CategoryService {

    public Boolean saveCategory(CategoryDTO categoryDTO);

    List<CategoryDTO> getAllCategory();

    List<CategoryResponse> getAllActiveCategory();

    CategoryDTO getCategoryById(Integer id) throws ResourceNotFoundException;

    Boolean deleteCategoryById(Integer id);
}
