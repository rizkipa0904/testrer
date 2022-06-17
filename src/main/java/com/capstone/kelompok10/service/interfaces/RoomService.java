package com.capstone.kelompok10.service.interfaces;

import java.util.List;

import org.springframework.data.domain.Page;

import com.capstone.kelompok10.model.dto.get.RoomDtoGet;
import com.capstone.kelompok10.model.dto.post.RoomDtoPost;
import com.capstone.kelompok10.model.entity.RoomEntity;

public interface RoomService {
    List<RoomEntity> findAll();
    List<RoomDtoGet> findAllDto();
    Page<RoomEntity> findAllPagination(int offset, int pageSize);
    Page<RoomEntity> findAllPaginationSort(int offset, int pageSize, String field);
    RoomEntity getRoomById(Long room_id);
    void createRoom(RoomEntity room);
    void createRoomDto(RoomDtoPost roomDtoPost);
    void updateRoom(Long room_id, RoomDtoPost roomDtoPost);
    void deleteRoom(Long room_id);
}
