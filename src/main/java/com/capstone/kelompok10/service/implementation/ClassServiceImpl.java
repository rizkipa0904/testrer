package com.capstone.kelompok10.service.implementation;

import java.util.ArrayList;
import java.util.List;

import com.capstone.kelompok10.model.dto.get.ClassDtoGet;
import com.capstone.kelompok10.model.dto.post.ClassDtoPost;
import com.capstone.kelompok10.model.entity.CategoryEntity;
import com.capstone.kelompok10.model.entity.ClassEntity;
import com.capstone.kelompok10.model.entity.InstructorEntity;
import com.capstone.kelompok10.model.entity.RoomEntity;
import com.capstone.kelompok10.model.entity.TypeEntity;
import com.capstone.kelompok10.repository.ClassRepository;
import com.capstone.kelompok10.service.interfaces.ClassService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ClassServiceImpl implements ClassService {
    ClassRepository classRepository;

    @Autowired
    public ClassServiceImpl(ClassRepository classRepository){
        this.classRepository = classRepository;
    }

    @Override
    public List<ClassEntity> findAll() {
        List<ClassEntity> instructor = new ArrayList<>();
        classRepository.findAll().forEach(instructor::add);
        return instructor;
    }
    
    @Override
    public Page<ClassEntity> findAllPagination(int offset, int pageSize) {
        Page<ClassEntity> instructor = classRepository.findAll(PageRequest.of(offset, pageSize));
        return instructor;
    }

    @Override
    public Page<ClassEntity> findAllPaginationSort(int offset, int pageSize, String field){
        Page<ClassEntity> instructor = classRepository.findAll(PageRequest.of(offset, pageSize).withSort(Sort.by(field)));
        return instructor;
    }

    @Override
    public List<ClassDtoGet> findAllDto() {
        List<ClassEntity> classs = classRepository.findAll();
        List<ClassDtoGet> classDtos = new ArrayList<>();
        
        classs.forEach(isi ->{
            ClassDtoGet dto = new ClassDtoGet();
            dto.setClassId(isi.getClassId());
            dto.setCapacity(isi.getCapacity());
            dto.setSchedule(isi.getSchedule().toString());
            dto.setPrice(isi.getPrice());
            dto.setStatus(isi.getStatus());
            dto.setImageUrl(isi.getImageUrl());
            dto.setInstructor(isi.getInstructor().getName());
            dto.setCategory(isi.getCategory().getName());
            dto.setRoom(isi.getRoom().getName());
            dto.setType(isi.getType().getName());

            classDtos.add(dto);
        });
        return classDtos;
    }

    @Override
    public ClassEntity getClassById(Long class_id) {
        return classRepository.findById(class_id).get();
    }

    @Override
    public void createClass(ClassEntity classes) {
        classRepository.save(classes);
    }

    @Override
    public void updateClass(Long class_id, ClassDtoPost classesDtoPost) {
        ClassEntity class2 = classRepository.findById(class_id).get();

        InstructorEntity instructorEntity = new InstructorEntity();
        instructorEntity.setInstructorId(classesDtoPost.getInstructorId());

        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setCategoryId(classesDtoPost.getCategoryId());

        RoomEntity roomEntity = new RoomEntity();
        roomEntity.setRoomId(classesDtoPost.getRoomId());

        TypeEntity typeEntity = new TypeEntity();
        typeEntity.setTypeId(classesDtoPost.getTypeId());

        class2.setStatus(classesDtoPost.getStatus());
        class2.setCapacity(classesDtoPost.getCapacity());
        class2.setSchedule(classesDtoPost.getSchedule());
        class2.setPrice(classesDtoPost.getPrice());
        class2.setImageUrl(classesDtoPost.getImageUrl());
        class2.setCategory(categoryEntity);
        class2.setInstructor(instructorEntity);
        class2.setRoom(roomEntity);
        class2.setType(typeEntity);
        
        classRepository.save(class2);
    }

    @Override
    public void deleteClass(Long class_id) {
        classRepository.deleteById(class_id);
        
    }

	@Override
	public void createClassDto(ClassDtoPost classDtoPost) {
		ClassEntity classEntity = new ClassEntity();

        InstructorEntity instructorEntity = new InstructorEntity();
        instructorEntity.setInstructorId(classDtoPost.getInstructorId());

        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setCategoryId(classDtoPost.getCategoryId());

        RoomEntity roomEntity = new RoomEntity();
        roomEntity.setRoomId(classDtoPost.getRoomId());

        TypeEntity typeEntity = new TypeEntity();
        typeEntity.setTypeId(classDtoPost.getTypeId());

        classEntity.setStatus(classDtoPost.getStatus());
        classEntity.setCapacity(classDtoPost.getCapacity());
        classEntity.setSchedule(classDtoPost.getSchedule());
        classEntity.setPrice(classDtoPost.getPrice());
        classEntity.setImageUrl(classDtoPost.getImageUrl());
        classEntity.setInstructor(instructorEntity);
        classEntity.setCategory(categoryEntity);
        classEntity.setRoom(roomEntity);
        classEntity.setType(typeEntity);

        classRepository.save(classEntity);

		
	}
}
