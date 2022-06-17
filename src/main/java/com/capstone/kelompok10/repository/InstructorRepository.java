package com.capstone.kelompok10.repository;

import com.capstone.kelompok10.model.entity.InstructorEntity;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstructorRepository extends JpaRepository<InstructorEntity, Long> {
    Optional<InstructorEntity> findById(Long instructor_id);
    InstructorEntity findByName(String name);
    Page<InstructorEntity> findAll(Pageable pageable);
}