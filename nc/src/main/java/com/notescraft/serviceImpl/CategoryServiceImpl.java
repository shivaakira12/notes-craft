package com.notescraft.serviceImpl;


import com.notescraft.dto.CategoryDTO;
import com.notescraft.dto.CategoryResponse;
import com.notescraft.entity.Category;
import com.notescraft.exception.ExistDataException;
import com.notescraft.exception.ResourceNotFoundException;
import com.notescraft.repository.CategoryRepository;
import com.notescraft.service.CategoryService;
import com.notescraft.utils.Validation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private Validation validation;
    @Override
    public Boolean saveCategory(CategoryDTO categoryDTO) {
        validation.categoryValidation(categoryDTO);
        Boolean exist = categoryRepository.existsByName(categoryDTO.getName().trim());
        if (exist) {

            throw new ExistDataException("Category already exist");
        }
        Category category = modelMapper.map(categoryDTO,Category.class);
        if(ObjectUtils.isEmpty(category.getId())){
            category.setName(categoryDTO.getName());
            category.setDescription(categoryDTO.getDescription());
            category.setIsActive(categoryDTO.getActive());
            category.setUpdatedOn(new Date());
            category.setUpdatedBy(1);
            category.setCreatedOn(new Date());
            category.setIsActive(categoryDTO.getActive());
        }
        else{
            updateCategoryById(category);
        }

        Category savedCategory = categoryRepository.save(category);
        return !ObjectUtils.isEmpty(savedCategory);
    }
    @Override
    public List<CategoryDTO> getAllCategory() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map(cat->modelMapper.map(cat,CategoryDTO.class)).toList();
    }

    @Override
    public List<CategoryResponse> getAllActiveCategory() {
        List<Category> categories = categoryRepository.findByIsActiveTrueAndIsDeletedFalse();
        return categories.stream().map(category -> modelMapper.map(category,CategoryResponse.class)).toList();
    }

    @Override
    public CategoryDTO getCategoryById(Integer id) throws ResourceNotFoundException {
    Category finCategory = categoryRepository.findByIdAndIsDeletedFalse(id).orElseThrow(()->new ResourceNotFoundException("Id Not Found"));
        if(!ObjectUtils.isEmpty(finCategory)){
                return  modelMapper.map(finCategory,CategoryDTO.class);
        }
        return null;
    }

    @Override
    public Boolean deleteCategoryById(Integer id) {
       Optional<Category> deleteCategory = categoryRepository.findById(id);
       if(deleteCategory.isPresent()){
           Category category = deleteCategory.get();
           category.setIsDeleted(true);
           categoryRepository.save(category);
           return true;
       }
       return false;
    }

    public void updateCategoryById(Category category) {
       Optional<Category> isCategoryPresent=categoryRepository.findById(category.getId());
       if(isCategoryPresent.isPresent())
       {
           Category existCategory = isCategoryPresent.get();
           category.setUpdatedBy(1);
           category.setCreatedOn(existCategory.getCreatedOn());
           category.setIsDeleted(existCategory.getIsDeleted());
           category.setUpdatedOn(new Date());
       }

    }
}
