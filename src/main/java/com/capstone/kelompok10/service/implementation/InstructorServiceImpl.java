package com.capstone.kelompok10.service.implementation;

import java.util.ArrayList;
import java.util.List;

import com.capstone.kelompok10.model.dto.get.InstructorDtoGet;
import com.capstone.kelompok10.model.dto.post.InstructorDtoPost;
import com.capstone.kelompok10.model.entity.InstructorEntity;
import com.capstone.kelompok10.repository.InstructorRepository;
import com.capstone.kelompok10.service.interfaces.InstructorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class InstructorServiceImpl implements InstructorService {
    InstructorRepository instructorRepository;

    @Autowired
    public InstructorServiceImpl(InstructorRepository instructorRepository){
        this.instructorRepository = instructorRepository;
    }
    
    @Override
    public List<InstructorEntity> findAll() {
        List<InstructorEntity> instructor = new ArrayList<>();
        instructorRepository.findAll().forEach(instructor::add);
        return instructor;
    }
    
    @Override
    public Page<InstructorEntity> findAllPagination(int offset, int pageSize) {
        Page<InstructorEntity> instructor = instructorRepository.findAll(PageRequest.of(offset, pageSize));
        return instructor;
    }

    @Override
    public Page<InstructorEntity> findAllPaginationSort(int offset, int pageSize, String field){
        Page<InstructorEntity> instructor = instructorRepository.findAll(PageRequest.of(offset, pageSize).withSort(Sort.by(field)));
        return instructor;
    }

    @Override
    public List<InstructorDtoGet> findAllDto() {
        List<InstructorEntity> instructors = instructorRepository.findAll();
        List<InstructorDtoGet> instructorDtos = new ArrayList<>();

        instructors.forEach(isi ->{
            InstructorDtoGet dto = new InstructorDtoGet();
            dto.setInstructorId(isi.getInstructorId());
            dto.setName(isi.getName());
            dto.setContact(isi.getContact());
            dto.setImageUrl(isi.getImageUrl());

            instructorDtos.add(dto);
        });
        return instructorDtos;
    }

    @Override
    public InstructorEntity getInstructorById(Long instructor_id) {
        return instructorRepository.findById(instructor_id).get();
    }

    @Override
    public void createInstructor(InstructorEntity instructor) {
        instructorRepository.save(instructor);
    }

    @Override
    public void updateInstructor(Long instructor_id, InstructorDtoPost instructorDtoPost) {
        InstructorEntity instructor2 = instructorRepository.findById(instructor_id).get();
        instructor2.setName(instructorDtoPost.getName());
        instructor2.setContact(instructorDtoPost.getContact());
        instructor2.setImageUrl(instructorDtoPost.getImageUrl());
        instructorRepository.save(instructor2);
    }

    @Override
    public void deleteInstructor(Long instructor_id) {
        instructorRepository.deleteById(instructor_id);
    }

	@Override
	public void createInstructorDto(InstructorDtoPost instructorDtoPost) {
		InstructorEntity instructorEntity = new InstructorEntity();
        instructorEntity.setName(instructorDtoPost.getName());
        instructorEntity.setContact(instructorDtoPost.getContact());
        instructorEntity.setImageUrl(instructorDtoPost.getImageUrl());
        
		instructorRepository.save(instructorEntity);
	}
}

