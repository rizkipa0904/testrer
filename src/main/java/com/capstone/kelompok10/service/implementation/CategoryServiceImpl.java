package com.capstone.kelompok10.service.implementation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.capstone.kelompok10.model.dto.get.CategoryDtoGet;
import com.capstone.kelompok10.model.dto.post.CategoryDtoPost;
import com.capstone.kelompok10.model.entity.CategoryEntity;
import com.capstone.kelompok10.repository.CategoryRepository;
import com.capstone.kelompok10.service.interfaces.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

    CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<CategoryEntity> findAll() {
        List<CategoryEntity> category = new ArrayList<>();
        categoryRepository.findAll().forEach(category::add);
        return category;
    }
    
    @Override
    public Page<CategoryEntity> findAllPagination(int offset, int pageSize) {
        Page<CategoryEntity> category = categoryRepository.findAll(PageRequest.of(offset, pageSize));
        return category;
    }

    @Override
    public Page<CategoryEntity> findAllPaginationSort(int offset, int pageSize, String field){
        Page<CategoryEntity> category = categoryRepository.findAll(PageRequest.of(offset, pageSize).withSort(Sort.by(field)));
        return category;
    }

    @Override
    public List<CategoryDtoGet> findAllDto() {
        List<CategoryEntity> categorys = categoryRepository.findAll();
        List<CategoryDtoGet> CategoryDtos = new ArrayList<>();
        
        categorys.forEach(isi ->{
            CategoryDtoGet dto = new CategoryDtoGet();
            dto.setCategoryId(isi.getCategoryId());
            dto.setName(isi.getName());

            CategoryDtos.add(dto);
        });
        return CategoryDtos;
    }

    @Override
    public CategoryEntity getCategoryById(Long category_id) {
        return categoryRepository.findById(category_id).get();
    }

    @Override
    public void createCategory(CategoryEntity category) {
        categoryRepository.save(category);
    }

    @Override
    public void updateCategory(Long category_id, CategoryDtoPost categoryDtoPost) {
        CategoryEntity category2 = categoryRepository.findById(category_id).get();
        category2.setName(categoryDtoPost.getName());

        categoryRepository.save(category2);
    }

    @Override
    public void deleteCategory(Long category_id) {
        categoryRepository.deleteById(category_id);
    }

	@Override
	public void createCategoryDto(CategoryDtoPost categoryDtoPost) {
		CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setName(categoryDtoPost.getName());
		
        categoryRepository.save(categoryEntity);
	}
}
