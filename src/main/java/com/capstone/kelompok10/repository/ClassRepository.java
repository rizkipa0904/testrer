package com.capstone.kelompok10.repository;

import com.capstone.kelompok10.model.entity.ClassEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassRepository extends JpaRepository<ClassEntity, Long> {
    ClassEntity findByStatus(Boolean status);
}