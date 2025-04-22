package com.notescraft.controller;

import com.notescraft.dto.CategoryDTO;
import com.notescraft.dto.CategoryResponse;
import com.notescraft.exception.ResourceNotFoundException;
import com.notescraft.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;
    @PostMapping("/save-category")
    public ResponseEntity<?> saveCategory(@RequestBody CategoryDTO categoryDTO)
    {
        Boolean saveCategory = categoryService.saveCategory(categoryDTO);
        if(saveCategory)
        {
            return new ResponseEntity<>("Category Successfully Saved", HttpStatus.CREATED);
        }
        else{
            return new ResponseEntity<>("Category Not Saved", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/")
    public ResponseEntity<?> getAllCategory() {

        List<CategoryDTO> allCategory = categoryService.getAllCategory();
        if (CollectionUtils.isEmpty(allCategory)) {
            return ResponseEntity.noContent().build();
        } else {
            return new ResponseEntity<>(allCategory, HttpStatus.OK);
        }

    }
    @GetMapping("/get-all-active-categories")
    public ResponseEntity<?> getAllActiveCategories()
    {
        List<CategoryResponse> allActiveCategory = categoryService.getAllActiveCategory();
        if (CollectionUtils.isEmpty(allActiveCategory)) {
            return ResponseEntity.noContent().build();
        } else {
            return new ResponseEntity<>(allActiveCategory, HttpStatus.OK);
        }
    }

    @GetMapping("/get-category-by-id/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable Integer id)  {
        CategoryDTO categoryDTO = null;
        try {
            categoryDTO = categoryService.getCategoryById(id);
        } catch (ResourceNotFoundException e) {
            throw new RuntimeException(e);
        }
        if(categoryDTO!=null)
       {
           return new ResponseEntity<>(categoryDTO,HttpStatus.OK);
       }
        return new ResponseEntity<>("Category Not Found with this Category Id : " +id, HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete-category-by-id/{id}")
    public ResponseEntity<?> deleteCategoryById(@PathVariable Integer id)
    {
        Boolean isCategoryDeleted = categoryService.deleteCategoryById(id);
        if(isCategoryDeleted)
        {
            return new ResponseEntity<>("Category Deleted Successfully ",HttpStatus.OK);
        }
        return new ResponseEntity<>("Internal Server Issue " +id, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

