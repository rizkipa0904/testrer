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

import com.capstone.kelompok10.model.dto.get.TypeDtoGet;
import com.capstone.kelompok10.model.dto.post.TypeDtoPost;
import com.capstone.kelompok10.model.entity.TypeEntity;
import com.capstone.kelompok10.service.interfaces.TypeService;

@RestController
@RequestMapping("/capstone/type")
public class TypeController {
    TypeService typeService;

    @Autowired
    public TypeController(TypeService typeService){
        this.typeService = typeService;
    }

    @GetMapping("/user")
    public ResponseEntity<List<TypeEntity>> findAll(){
        List<TypeEntity> types = typeService.findAll();
        return new ResponseEntity<>(types, HttpStatus.OK);
    }

    @GetMapping("/user/{offset}/{pageSize}")
    public ResponseEntity<Page<TypeEntity>> findAllPaginationSorting(@PathVariable int offset,@PathVariable int pageSize){
        Page<TypeEntity> types = typeService.findAllPagination(offset, pageSize);
        return new ResponseEntity<>(types, HttpStatus.OK);
    }

    @GetMapping("/user/{offset}/{pageSize}/{field}")
    public ResponseEntity<Page<TypeEntity>> findAllPaginationSorting(@PathVariable int offset,@PathVariable int pageSize,@PathVariable String field){
        Page<TypeEntity> types = typeService.findAllPaginationSort(offset, pageSize, field);
        return new ResponseEntity<>(types, HttpStatus.OK);
    }

    @GetMapping("/dto")
    public ResponseEntity<List<TypeDtoGet>> findAllDto(){
        List<TypeDtoGet> typeDtos = typeService.findAllDto();
        return new ResponseEntity<>(typeDtos, HttpStatus.OK);
    }

    @GetMapping("/user/{type_id}")
    public ResponseEntity<TypeEntity> getTypeById(@PathVariable Long type_id){
        return new ResponseEntity<>(typeService.getTypeById(type_id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<TypeEntity> createType(@RequestBody TypeEntity type){
        typeService.createType(type);
        return new ResponseEntity<>(type, HttpStatus.OK);
    }

    @PostMapping("/dto")
    public ResponseEntity<TypeDtoPost> createTypeDto(@RequestBody TypeDtoPost typeDtoPost){
        typeService.createTypeDto(typeDtoPost);
        return new ResponseEntity<>(typeDtoPost, HttpStatus.OK);
    }

    @PutMapping("/{type_id}")
    public ResponseEntity<TypeEntity> updateType(@PathVariable("Type_id") Long type_id, @RequestBody TypeDtoPost typeDtoPost){
        typeService.updateType(type_id, typeDtoPost);
        return new ResponseEntity<>(typeService.getTypeById(type_id), HttpStatus.OK);
    }

    @DeleteMapping("/{type_id}")
    public ResponseEntity<TypeEntity> deleteType(@PathVariable("type_id") Long type_id){
        typeService.deleteType(type_id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
