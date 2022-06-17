package com.capstone.kelompok10.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.kelompok10.model.dto.get.CategoryDtoGet;
import com.capstone.kelompok10.model.dto.post.CategoryDtoPost;
import com.capstone.kelompok10.model.entity.CategoryEntity;
import com.capstone.kelompok10.service.interfaces.CategoryService;

@RestController
@RequestMapping("/capstone/category")
public class CategoryController {
    CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService){
        this.categoryService = categoryService;
    }

    @GetMapping("/user")
    public ResponseEntity<List<CategoryEntity>> findAll(){
        List<CategoryEntity> categorys = categoryService.findAll();
        return new ResponseEntity<>(categorys, HttpStatus.OK);
    }

    @GetMapping("/user/{offset}/{pageSize}")
    public ResponseEntity<Page<CategoryEntity>> findAllPaginationSorting(@PathVariable int offset,@PathVariable int pageSize){
        Page<CategoryEntity> categorys = categoryService.findAllPagination(offset, pageSize);
        return new ResponseEntity<>(categorys, HttpStatus.OK);
    }

    @GetMapping("/user/{offset}/{pageSize}/{field}")
    public ResponseEntity<Page<CategoryEntity>> findAllPaginationSorting(@PathVariable int offset,@PathVariable int pageSize,@PathVariable String field){
        Page<CategoryEntity> categorys = categoryService.findAllPaginationSort(offset, pageSize, field);
        return new ResponseEntity<>(categorys, HttpStatus.OK);
    }

    @GetMapping("/dto")
    public ResponseEntity<List<CategoryDtoGet>> findAllDto(){
        List<CategoryDtoGet> categoryDtos = categoryService.findAllDto();
        return new ResponseEntity<>(categoryDtos, HttpStatus.OK);
    }

    @GetMapping("/user/{category_id}")
    public ResponseEntity<CategoryEntity> getCategoryById(@PathVariable Long category_id){
        return new ResponseEntity<>(categoryService.getCategoryById(category_id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CategoryEntity> createCategory(@RequestBody CategoryEntity category){
        categoryService.createCategory(category);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @PostMapping("/dto")
    public ResponseEntity<CategoryDtoPost> createCategoryDto(@RequestBody CategoryDtoPost categoryDtoPost){
        categoryService.createCategoryDto(categoryDtoPost);
        return new ResponseEntity<>(categoryDtoPost, HttpStatus.OK);
    }

    @PutMapping("/{category_id}")
    public ResponseEntity<CategoryEntity> updateCategory(@PathVariable("category_id") Long category_id, @RequestBody CategoryDtoPost categoryDtoPost){
        categoryService.updateCategory(category_id, categoryDtoPost);
        return new ResponseEntity<>(categoryService.getCategoryById(category_id), HttpStatus.OK);
    }

    @DeleteMapping("/{category_id}")
    public ResponseEntity<CategoryEntity> deleteCategory(@PathVariable("category_id") Long category_id){
        categoryService.deleteCategory(category_id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
