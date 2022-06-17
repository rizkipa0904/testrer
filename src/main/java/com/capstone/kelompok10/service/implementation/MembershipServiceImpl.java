package com.capstone.kelompok10.service.implementation;

import java.util.ArrayList;
import java.util.List;

import com.capstone.kelompok10.model.dto.get.MembershipDtoGet;
import com.capstone.kelompok10.model.dto.post.MembershipDtoPost;
import com.capstone.kelompok10.model.entity.MemberEntity;
import com.capstone.kelompok10.model.entity.MembershipEntity;
import com.capstone.kelompok10.model.entity.UserEntity;
import com.capstone.kelompok10.repository.MembershipRepository;
import com.capstone.kelompok10.service.interfaces.MembershipService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class MembershipServiceImpl implements MembershipService {
    MembershipRepository membershipRepository;

    @Autowired
    public MembershipServiceImpl(MembershipRepository membershipRepository){
        this.membershipRepository = membershipRepository;
    }

    @Override
    public List<MembershipEntity> findAll() {
        List<MembershipEntity> membership = new ArrayList<>();
        membershipRepository.findAll().forEach(membership::add);
        return membership;
    }
    
    @Override
    public Page<MembershipEntity> findAllPagination(int offset, int pageSize) {
        Page<MembershipEntity> membership = membershipRepository.findAll(PageRequest.of(offset, pageSize));
        return membership;
    }

    @Override
    public Page<MembershipEntity> findAllPaginationSort(int offset, int pageSize, String field){
        Page<MembershipEntity> membership = membershipRepository.findAll(PageRequest.of(offset, pageSize).withSort(Sort.by(field)));
        return membership;
    }

    @Override
    public List<MembershipDtoGet> findAllDto() {
        List<MembershipEntity> memberships = membershipRepository.findAll();
        List<MembershipDtoGet> membershipDtos = new ArrayList<>();
        
        memberships.forEach(isi ->{
            MembershipDtoGet dto = new MembershipDtoGet();
            dto.setMembershipId(isi.getMembershipId());
            dto.setStatus(isi.getStatus());
            dto.setUser(isi.getUser().getName());
            dto.setMember(isi.getMember().getMemberId().toString());

            membershipDtos.add(dto);
        });
        return membershipDtos;
    }

    @Override
    public MembershipEntity getMembershipById(Long membership_id) {
        return membershipRepository.findById(membership_id).get();
    }

    @Override
    public void createMembership(MembershipEntity membership) {
        membershipRepository.save(membership);
    }

    @Override
    public void updateMembership(Long membership_id, MembershipDtoPost membershipDtoPost) {
        MembershipEntity membership2 = membershipRepository.findById(membership_id).get();

        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setMemberId(membershipDtoPost.getMemberId());

        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(membershipDtoPost.getUserId()); 

        membership2.setStatus(membershipDtoPost.getStatus());
        membership2.setUser(userEntity);
        membership2.setMember(memberEntity);

        membershipRepository.save(membership2);
    }

    @Override
    public void deleteMembership(Long membership_id) {
        membershipRepository.deleteById(membership_id);
        
    }

    @Override
    public void createMembershipDto(MembershipDtoPost membershipDtoPost) {
        MembershipEntity membershipEntity = new MembershipEntity();

        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(membershipDtoPost.getUserId());

        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setMemberId(membershipDtoPost.getMemberId());

        membershipEntity.setStatus(membershipDtoPost.getStatus());
        membershipEntity.setUser(userEntity);
        membershipEntity.setMember(memberEntity);

        membershipRepository.save(membershipEntity);
    }
}
