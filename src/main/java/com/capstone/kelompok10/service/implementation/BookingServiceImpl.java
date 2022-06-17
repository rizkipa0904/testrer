package com.capstone.kelompok10.service.implementation;

import java.util.ArrayList;
import java.util.List;

import com.capstone.kelompok10.model.dto.get.BookingDtoGet;
import com.capstone.kelompok10.model.dto.post.BookingDtoPost;
import com.capstone.kelompok10.model.entity.BookingEntity;
import com.capstone.kelompok10.model.entity.ClassEntity;
import com.capstone.kelompok10.model.entity.UserEntity;
import com.capstone.kelompok10.repository.BookingRepository;
import com.capstone.kelompok10.service.interfaces.BookingService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    BookingRepository bookingRepository;

    @Autowired
    public BookingServiceImpl(BookingRepository bookingRepository){
        this.bookingRepository = bookingRepository;
    }

    @Override
    public List<BookingEntity> findAll() {
        List<BookingEntity> booking = new ArrayList<>();
        bookingRepository.findAll().forEach(booking::add);
        return booking;
    }
    
    @Override
    public Page<BookingEntity> findAllPagination(int offset, int pageSize) {
        Page<BookingEntity> booking = bookingRepository.findAll(PageRequest.of(offset, pageSize));
        return booking;
    }

    @Override
    public Page<BookingEntity> findAllPaginationSort(int offset, int pageSize, String field){
        Page<BookingEntity> booking = bookingRepository.findAll(PageRequest.of(offset, pageSize).withSort(Sort.by(field)));
        return booking;
    }

    @Override
    public List<BookingDtoGet> findAllDto() {
        List<BookingEntity> bookings = bookingRepository.findAll();
        List<BookingDtoGet> bookingDtos = new ArrayList<>();
        
        bookings.forEach(isi ->{
            BookingDtoGet dto = new BookingDtoGet();
            dto.setBookingId(isi.getBookingId());
            dto.setStatus(isi.getStatus().toString());
            dto.setUser(isi.getUser().getName());
            dto.setClasses(isi.getClasses().getClassId());

            bookingDtos.add(dto);
        });
        return bookingDtos;
    }

    @Override
    public BookingEntity getBookingById(Long booking_id) {
        if(bookingRepository.findById(booking_id) == null){
            log.info("Booking id not found");
            return null;
        }
        log.info("Booking with id {} found", booking_id);
        return bookingRepository.findById(booking_id).get();            
    }

    @Override
    public void createBooking(BookingEntity booking) {
        bookingRepository.save(booking);
    }

    @Override
    public void updateBooking(Long booking_id, BookingDtoPost bookingDtoPost) {
        BookingEntity booking2 = bookingRepository.findById(booking_id).get();
        
        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(bookingDtoPost.getUserId());

        ClassEntity classEntity = new ClassEntity();
        classEntity.setClassId(bookingDtoPost.getClassId());

        booking2.setStatus(bookingDtoPost.getStatus());
        booking2.setUser(userEntity);
        booking2.setClasses(classEntity);

        bookingRepository.save(booking2);
    }

    @Override
    public void deleteBooking(Long booking_id) {
        bookingRepository.deleteById(booking_id);
    }

    @Override
    public void createBookingDto(BookingDtoPost bookingDtoPost) {
        BookingEntity bookingEntity = new BookingEntity();
        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(bookingDtoPost.getUserId());
        ClassEntity classEntity = new ClassEntity();
        classEntity.setClassId(bookingDtoPost.getClassId());

        bookingEntity.setStatus(bookingDtoPost.getStatus());
        bookingEntity.setClasses(classEntity);
        bookingEntity.setUser(userEntity);
        
        bookingRepository.save(bookingEntity);
    }
}
