package com.capstone.kelompok10.service.interfaces;

import java.util.List;

import org.springframework.data.domain.Page;

import com.capstone.kelompok10.model.dto.get.RoleDtoGet;
import com.capstone.kelompok10.model.dto.post.RoleDtoPost;
import com.capstone.kelompok10.model.entity.RoleEntity;

public interface RoleService {
    List<RoleEntity> findAll();
    List<RoleDtoGet> findAllDto();
    Page<RoleEntity> findAllPagination(int offset, int pageSize);
    Page<RoleEntity> findAllPaginationSort(int offset, int pageSize, String field);
    RoleEntity getRoleById(Long role_id);
    void createRole(RoleEntity role);
    void createRoleDto(RoleDtoPost roleDtoPost);
    void updateRole(Long role_id, RoleDtoPost roleDtoPost);
    void deleteRole(Long role_id);
}
