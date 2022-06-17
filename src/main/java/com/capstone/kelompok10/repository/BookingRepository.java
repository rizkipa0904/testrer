package com.capstone.kelompok10.repository;

import com.capstone.kelompok10.model.entity.BookingEntity;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<BookingEntity, Long> {
    Optional<BookingEntity> findById(Long booking_id);
    BookingEntity findByStatus(Boolean status);
    Page<BookingEntity> findAll(Pageable pageable);
}
