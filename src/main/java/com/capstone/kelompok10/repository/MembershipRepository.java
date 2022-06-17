package com.capstone.kelompok10.repository;

import com.capstone.kelompok10.model.entity.MembershipEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MembershipRepository extends JpaRepository<MembershipEntity, Long> {
    MembershipEntity findByStatus(Boolean status);
}
