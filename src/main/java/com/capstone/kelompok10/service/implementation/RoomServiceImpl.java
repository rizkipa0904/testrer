package com.capstone.kelompok10.service.implementation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.capstone.kelompok10.model.dto.get.RoomDtoGet;
import com.capstone.kelompok10.model.dto.post.RoomDtoPost;
import com.capstone.kelompok10.model.entity.RoomEntity;
import com.capstone.kelompok10.repository.RoomRepository;
import com.capstone.kelompok10.service.interfaces.RoomService;

@Service
public class RoomServiceImpl implements RoomService {
    RoomRepository roomRepository;

    @Autowired
    public RoomServiceImpl(RoomRepository roomRepository){
        this.roomRepository = roomRepository;
    }

    @Override
    public List<RoomEntity> findAll() {
        List<RoomEntity> room = new ArrayList<>();
        roomRepository.findAll().forEach(room::add);
        return room;
    }
    
    @Override
    public Page<RoomEntity> findAllPagination(int offset, int pageSize) {
        Page<RoomEntity> room = roomRepository.findAll(PageRequest.of(offset, pageSize));
        return room;
    }

    @Override
    public Page<RoomEntity> findAllPaginationSort(int offset, int pageSize, String field){
        Page<RoomEntity> room = roomRepository.findAll(PageRequest.of(offset, pageSize).withSort(Sort.by(field)));
        return room;
    }

    @Override
    public List<RoomDtoGet> findAllDto() {
        List<RoomEntity> rooms = roomRepository.findAll();
        List<RoomDtoGet> roomDtos = new ArrayList<>();
        
        rooms.forEach(isi ->{
            RoomDtoGet dto = new RoomDtoGet();
            dto.setRoomId(isi.getRoomId());
            dto.setName(isi.getName());

            roomDtos.add(dto);
        });
        return roomDtos;
    }

    @Override
    public RoomEntity getRoomById(Long room_id) {
        return roomRepository.findById(room_id).get();
    }

    @Override
    public void createRoom(RoomEntity room) {
        roomRepository.save(room);
    }

    @Override
    public void updateRoom(Long room_id, RoomDtoPost roomDtoPost) {
        RoomEntity room2 = roomRepository.findById(room_id).get();
        room2.setName(roomDtoPost.getName());

        roomRepository.save(room2);
    }

    @Override
    public void deleteRoom(Long room_id) {
        roomRepository.deleteById(room_id);
    }

	@Override
	public void createRoomDto(RoomDtoPost roomDtoPost) {
		RoomEntity roomEntity = new RoomEntity();
        roomEntity.setName(roomDtoPost.getName());
		
        roomRepository.save(roomEntity);
	}
}
