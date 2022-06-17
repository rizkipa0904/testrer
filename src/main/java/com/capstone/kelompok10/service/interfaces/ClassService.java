package com.capstone.kelompok10.service.interfaces;

import java.util.List;

import org.springframework.data.domain.Page;

import com.capstone.kelompok10.model.dto.get.ClassDtoGet;
import com.capstone.kelompok10.model.dto.post.ClassDtoPost;
import com.capstone.kelompok10.model.entity.ClassEntity;

public interface ClassService {
    List<ClassEntity> findAll();
    List<ClassDtoGet> findAllDto();
    Page<ClassEntity> findAllPagination(int offset, int pageSize);
    Page<ClassEntity> findAllPaginationSort(int offset, int pageSize, String field);
    ClassEntity getClassById(Long class_id);
    void createClass(ClassEntity classes);
    void createClassDto(ClassDtoPost classDtoPost);
    void updateClass(Long class_id, ClassDtoPost classesDtoPost);
    void deleteClass(Long class_id);
}
