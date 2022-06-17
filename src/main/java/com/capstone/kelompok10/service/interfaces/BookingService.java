package com.capstone.kelompok10.service.interfaces;

import java.util.List;

import org.springframework.data.domain.Page;

import com.capstone.kelompok10.model.dto.get.BookingDtoGet;
import com.capstone.kelompok10.model.dto.post.BookingDtoPost;
import com.capstone.kelompok10.model.entity.BookingEntity;

public interface BookingService {
    List<BookingEntity> findAll();
    List<BookingDtoGet> findAllDto();
    Page<BookingEntity> findAllPagination(int offset, int pageSize);
    Page<BookingEntity> findAllPaginationSort(int offset, int pageSize, String field);
    BookingEntity getBookingById(Long booking_id);
    void createBooking(BookingEntity booking);
    void createBookingDto(BookingDtoPost bookingDtoPost);
    void updateBooking(Long booking_id, BookingDtoPost bookingDtoPost);
    void deleteBooking(Long booking_id);
}
