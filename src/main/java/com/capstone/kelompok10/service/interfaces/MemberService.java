package com.capstone.kelompok10.service.interfaces;

import java.util.List;

import org.springframework.data.domain.Page;

import com.capstone.kelompok10.model.dto.get.MemberDtoGet;
import com.capstone.kelompok10.model.dto.post.MemberDtoPost;
import com.capstone.kelompok10.model.entity.MemberEntity;

public interface MemberService {
    List<MemberEntity> findAll();
    List<MemberDtoGet> findAllDto();
    Page<MemberEntity> findAllPagination(int offset, int pageSize);
    Page<MemberEntity> findAllPaginationSort(int offset, int pageSize, String field);
    MemberEntity getMemberById(Long member_id);
    void createMember(MemberEntity member);
    void createMemberDto(MemberDtoPost memberDtoPost);
    void updateMember(Long member_id, MemberDtoPost memberDtoPost);
    void deleteMember(Long member_id);
}
