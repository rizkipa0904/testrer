package com.capstone.kelompok10.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.capstone.kelompok10.model.entity.TypeEntity;

@Repository
public interface TypeRepository extends JpaRepository<TypeEntity, Long> {
    Optional<TypeEntity> findById(Long type_id);
    TypeEntity findByName(String name);
}
