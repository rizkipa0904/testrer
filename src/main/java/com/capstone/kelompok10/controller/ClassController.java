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

import com.capstone.kelompok10.model.dto.get.ClassDtoGet;
import com.capstone.kelompok10.model.dto.post.ClassDtoPost;
import com.capstone.kelompok10.model.entity.ClassEntity;
import com.capstone.kelompok10.service.interfaces.ClassService;

@RestController
@RequestMapping("/capstone/class")
public class ClassController {
    ClassService classService;

    @Autowired
    public ClassController(ClassService classService){
        this.classService = classService;
    }

    @GetMapping("/user")
    public ResponseEntity<List<ClassEntity>> findAll(){
        List<ClassEntity> classs = classService.findAll();
        return new ResponseEntity<>(classs, HttpStatus.OK);
    }

    @GetMapping("/user/{offset}/{pageSize}")
    public ResponseEntity<Page<ClassEntity>> findAllPaginationSorting(@PathVariable int offset,@PathVariable int pageSize){
        Page<ClassEntity> classs = classService.findAllPagination(offset, pageSize);
        return new ResponseEntity<>(classs, HttpStatus.OK);
    }

    @GetMapping("/user/{offset}/{pageSize}/{field}")
    public ResponseEntity<Page<ClassEntity>> findAllPaginationSorting(@PathVariable int offset,@PathVariable int pageSize,@PathVariable String field){
        Page<ClassEntity> classs = classService.findAllPaginationSort(offset, pageSize, field);
        return new ResponseEntity<>(classs, HttpStatus.OK);
    }

    @GetMapping("/user/dto")
    public ResponseEntity<List<ClassDtoGet>> findAllDto(){
        List<ClassDtoGet> classDtos = classService.findAllDto();
        return new ResponseEntity<>(classDtos, HttpStatus.OK);
    }

    @GetMapping("/user/{class_id}")
    public ResponseEntity<ClassEntity> getClassById(@PathVariable("class_id") Long class_id){
        return new ResponseEntity<>(classService.getClassById(class_id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ClassEntity> createClass(@RequestBody ClassEntity classes){
        classService.createClass(classes);
        return new ResponseEntity<>(classes, HttpStatus.OK);
    }

    @PostMapping("/dto")
    public ResponseEntity<ClassDtoPost> createClassDto(@RequestBody ClassDtoPost classDtoPost){
        classService.createClassDto(classDtoPost);
        return new ResponseEntity<>(classDtoPost, HttpStatus.OK);
    }

    @PutMapping("/{class_id}")
    public ResponseEntity<ClassEntity> updateClass(@PathVariable("class_id") Long class_id, @RequestBody ClassDtoPost classesDtoPost){
        classService.updateClass(class_id, classesDtoPost);
        return new ResponseEntity<>(classService.getClassById(class_id), HttpStatus.OK);
    }

    @DeleteMapping("/{class_id}")
    public ResponseEntity<ClassEntity> deleteClass(@PathVariable("class_id") Long class_id){
        classService.deleteClass(class_id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
