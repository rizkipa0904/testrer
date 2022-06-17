package com.capstone.kelompok10.service.implementation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.capstone.kelompok10.model.dto.get.MemberDtoGet;
import com.capstone.kelompok10.model.dto.post.MemberDtoPost;
import com.capstone.kelompok10.model.entity.MemberEntity;
import com.capstone.kelompok10.repository.MemberRepository;
import com.capstone.kelompok10.service.interfaces.MemberService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    MemberRepository memberRepository;

    @Autowired
    public MemberServiceImpl(MemberRepository memberRepository){
        this.memberRepository = memberRepository;
    }

    @Override
    public List<MemberEntity> findAll() {
        List<MemberEntity> member = new ArrayList<>();
        memberRepository.findAll().forEach(member::add);
        return member;
    }
    
    @Override
    public Page<MemberEntity> findAllPagination(int offset, int pageSize) {
        Page<MemberEntity> member = memberRepository.findAll(PageRequest.of(offset, pageSize));
        return member;
    }

    @Override
    public Page<MemberEntity> findAllPaginationSort(int offset, int pageSize, String field){
        Page<MemberEntity> member = memberRepository.findAll(PageRequest.of(offset, pageSize).withSort(Sort.by(field)));
        return member;
    }

    @Override
    public List<MemberDtoGet> findAllDto() {
        List<MemberEntity> members = memberRepository.findAll();
        List<MemberDtoGet> memberDtos = new ArrayList<>();
        
        members.forEach(isi ->{
            MemberDtoGet dto = new MemberDtoGet();
            dto.setMemberId(isi.getMemberId());
            dto.setLength(isi.getLength());
            dto.setPrice(isi.getPrice());

            memberDtos.add(dto);
        });
        return memberDtos;
    }

    @Override
    public MemberEntity getMemberById(Long member_id) {
        return memberRepository.findById(member_id).get();
    }

    @Override
    public void createMember(MemberEntity member) {
        memberRepository.save(member);
    }

    @Override
    public void updateMember(Long member_id, MemberDtoPost memberDtoPost) {
        MemberEntity member2 = memberRepository.findById(member_id).get();
        member2.setLength(memberDtoPost.getLength());
        member2.setPrice(memberDtoPost.getPrice());

        memberRepository.save(member2);
    }

    @Override
    public void deleteMember(Long member_id) {
        memberRepository.deleteById(member_id);
    }

	@Override
	public void createMemberDto(MemberDtoPost memberDtoPost) {
		MemberEntity memberEntity = new MemberEntity();
        memberEntity.setLength(memberDtoPost.getLength());
        memberEntity.setPrice(memberDtoPost.getPrice());

        memberRepository.save(memberEntity);
	}
}
