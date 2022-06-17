package com.capstone.kelompok10.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.kelompok10.model.dto.get.RoomDtoGet;
import com.capstone.kelompok10.model.dto.post.RoomDtoPost;
import com.capstone.kelompok10.model.entity.RoomEntity;
import com.capstone.kelompok10.service.interfaces.RoomService;

@RestController
@RequestMapping("/capstone/room")
public class RoomController {
    RoomService roomService;

    @Autowired
    public RoomController(RoomService roomService){
        this.roomService = roomService;
    }

    @GetMapping("/user")
    public ResponseEntity<List<RoomEntity>> findAll(){
        List<RoomEntity> rooms = roomService.findAll();
        return new ResponseEntity<>(rooms, HttpStatus.OK);
    }

    @GetMapping("/user/{offset}/{pageSize}")
    public ResponseEntity<Page<RoomEntity>> findAllPaginationSorting(@PathVariable int offset,@PathVariable int pageSize){
        Page<RoomEntity> rooms = roomService.findAllPagination(offset, pageSize);
        return new ResponseEntity<>(rooms, HttpStatus.OK);
    }

    @GetMapping("/user/{offset}/{pageSize}/{field}")
    public ResponseEntity<Page<RoomEntity>> findAllPaginationSorting(@PathVariable int offset,@PathVariable int pageSize,@PathVariable String field){
        Page<RoomEntity> rooms = roomService.findAllPaginationSort(offset, pageSize, field);
        return new ResponseEntity<>(rooms, HttpStatus.OK);
    }

    @GetMapping("/user/dto")
    public ResponseEntity<List<RoomDtoGet>> findAllDto(){
        List<RoomDtoGet> roomDtos = roomService.findAllDto();
        return new ResponseEntity<>(roomDtos, HttpStatus.OK);
    }

    @GetMapping("/user/{room_id}")
    public ResponseEntity<RoomEntity> getRoomById(@PathVariable Long room_id){
        return new ResponseEntity<>(roomService.getRoomById(room_id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<RoomEntity> createRoom(@RequestBody RoomEntity room){
        roomService.createRoom(room);
        return new ResponseEntity<>(room, HttpStatus.OK);
    }

    @PostMapping("/dto")
    public ResponseEntity<RoomDtoPost> createRoomDto(@RequestBody RoomDtoPost roomDtoPost){
        roomService.createRoomDto(roomDtoPost);
        return new ResponseEntity<>(roomDtoPost, HttpStatus.OK);
    }

    @PutMapping("/{room_id}")
    public ResponseEntity<RoomEntity> updateRoom(@PathVariable("room_id") Long room_id, @RequestBody RoomDtoPost roomDtoPost){
        roomService.updateRoom(room_id, roomDtoPost);
        return new ResponseEntity<>(roomService.getRoomById(room_id), HttpStatus.OK);
    }

    @DeleteMapping("/{room_id}")
    public ResponseEntity<RoomEntity> deleteRoom(@PathVariable("room_id") Long room_id){
        roomService.deleteRoom(room_id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
