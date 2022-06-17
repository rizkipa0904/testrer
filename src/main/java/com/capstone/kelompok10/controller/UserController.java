package com.capstone.kelompok10.controller;

import com.capstone.kelompok10.model.dto.get.UserDtoGet;
import com.capstone.kelompok10.model.dto.post.UserDtoPost;
import com.capstone.kelompok10.model.entity.UserEntity;
import com.capstone.kelompok10.model.payload.RoleToUser;
import com.capstone.kelompok10.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/capstone/user")
public class UserController {
    UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserEntity>> findAll(){
        List<UserEntity> users = userService.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{offset}/{pageSize}")
    public ResponseEntity<Page<UserEntity>> findAllPaginationSorting(@PathVariable int offset,@PathVariable int pageSize){
        Page<UserEntity> users = userService.findAllPagination(offset, pageSize);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{offset}/{pageSize}/{field}")
    public ResponseEntity<Page<UserEntity>> findAllPaginationSorting(@PathVariable int offset,@PathVariable int pageSize,@PathVariable String field){
        Page<UserEntity> users = userService.findAllPaginationSort(offset, pageSize, field);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/dto")
    public ResponseEntity<List<UserDtoGet>> findAllDto(){
        List<UserDtoGet> userDtos = userService.findAllDto();
        return new ResponseEntity<>(userDtos, HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<UserEntity> getUserById(@RequestParam(required = false) Long user_id, @RequestParam(required = false) String name){
        return new ResponseEntity<>(userService.getUserById(user_id), HttpStatus.OK);
    }

    @PutMapping("/user/{user_id}")
    public ResponseEntity<UserEntity> updateUser(@PathVariable("user_id") Long user_id, @RequestBody UserDtoPost userDtoPost){
        userService.updateUser(user_id, userDtoPost);
        return new ResponseEntity<>(userService.getUserById(user_id), HttpStatus.OK);
    }

    @DeleteMapping("/{user_id}")
    public ResponseEntity<UserEntity> deleteUser(@PathVariable("user_id") Long user_id){
        userService.deleteUser(user_id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/addRole")
    public ResponseEntity<?> addRoleToUser(@RequestBody RoleToUser form){
        userService.addRoleToUser(form.getUsername(), form.getRoleName());
        return ResponseEntity.ok().build();
    }
}
