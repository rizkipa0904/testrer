package com.capstone.kelompok10.controller;

import java.util.List;

import com.capstone.kelompok10.model.dto.get.MembershipDtoGet;
import com.capstone.kelompok10.model.dto.post.MembershipDtoPost;
import com.capstone.kelompok10.model.entity.MembershipEntity;
import com.capstone.kelompok10.service.interfaces.MembershipService;

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

@RestController
@RequestMapping("/capstone/membership")
public class MembershipController {
    MembershipService membershipService;

    @Autowired
    public MembershipController(MembershipService membershipService){
        this.membershipService = membershipService;
    }

    @GetMapping("/user")
    public ResponseEntity<List<MembershipEntity>> findAll(){
        List<MembershipEntity> memberships = membershipService.findAll();
        return new ResponseEntity<>(memberships, HttpStatus.OK);
    }

    @GetMapping("/user/{offset}/{pageSize}")
    public ResponseEntity<Page<MembershipEntity>> findAllPaginationSorting(@PathVariable int offset,@PathVariable int pageSize){
        Page<MembershipEntity> memberships = membershipService.findAllPagination(offset, pageSize);
        return new ResponseEntity<>(memberships, HttpStatus.OK);
    }

    @GetMapping("/user/{offset}/{pageSize}/{field}")
    public ResponseEntity<Page<MembershipEntity>> findAllPaginationSorting(@PathVariable int offset,@PathVariable int pageSize,@PathVariable String field){
        Page<MembershipEntity> memberships = membershipService.findAllPaginationSort(offset, pageSize, field);
        return new ResponseEntity<>(memberships, HttpStatus.OK);
    }

    @GetMapping("/user/dto")
    public ResponseEntity<List<MembershipDtoGet>> findAllDto(){
        List<MembershipDtoGet> membershipDtos = membershipService.findAllDto();
        return new ResponseEntity<>(membershipDtos, HttpStatus.OK);
    }

    @GetMapping("/user/{membership_id}")
    public ResponseEntity<MembershipEntity> getMembershipById(@PathVariable("membership_id") Long membership_id){
        return new ResponseEntity<>(membershipService.getMembershipById(membership_id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<MembershipEntity> createMembership(@RequestBody MembershipEntity membership){
        membershipService.createMembership(membership);
        return new ResponseEntity<>(membership, HttpStatus.OK);
    }

    @PostMapping("/dto")
    public ResponseEntity<MembershipDtoPost> createMemberDto(@RequestBody MembershipDtoPost membershipDtoPost){
        membershipService.createMembershipDto(membershipDtoPost);
        return new ResponseEntity<>(membershipDtoPost, HttpStatus.OK);
    }

    @PutMapping("/{membership_id}")
    public ResponseEntity<MembershipEntity> updateMembership(@PathVariable("membership_id") Long membership_id, @RequestBody MembershipDtoPost membershipDtoPost){
        membershipService.updateMembership(membership_id, membershipDtoPost);
        return new ResponseEntity<>(membershipService.getMembershipById(membership_id), HttpStatus.OK);
    }

    @DeleteMapping("/{membership_id}")
    public ResponseEntity<MembershipEntity> deleteMembership(@PathVariable("membership_id") Long membership_id){
        membershipService.deleteMembership(membership_id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
