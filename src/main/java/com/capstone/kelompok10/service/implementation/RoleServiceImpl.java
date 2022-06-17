package com.capstone.kelompok10.service.implementation;

import java.util.ArrayList;
import java.util.List;

import com.capstone.kelompok10.model.dto.get.RoleDtoGet;
import com.capstone.kelompok10.model.dto.post.RoleDtoPost;
import com.capstone.kelompok10.model.entity.RoleEntity;
import com.capstone.kelompok10.repository.RoleRepository;
import com.capstone.kelompok10.repository.UserRepository;
import com.capstone.kelompok10.service.interfaces.RoleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository){
        this.roleRepository = roleRepository;
    }

    @Override
    public List<RoleEntity> findAll() {
        List<RoleEntity> role = new ArrayList<>();
        roleRepository.findAll().forEach(role::add);
        return role;
    }
    
    @Override
    public Page<RoleEntity> findAllPagination(int offset, int pageSize) {
        Page<RoleEntity> role = roleRepository.findAll(PageRequest.of(offset, pageSize));
        return role;
    }

    @Override
    public Page<RoleEntity> findAllPaginationSort(int offset, int pageSize, String field){
        Page<RoleEntity> role = roleRepository.findAll(PageRequest.of(offset, pageSize).withSort(Sort.by(field)));
        return role;
    }

    @Override
    public List<RoleDtoGet> findAllDto() {
        List<RoleEntity> roles = roleRepository.findAll();
        List<RoleDtoGet> roleDtos = new ArrayList<>();
        
        roles.forEach(isi ->{
            RoleDtoGet dto = new RoleDtoGet();
            dto.setRoleId(isi.getRoleId());
            dto.setName(isi.getName());
            
            roleDtos.add(dto);
        });
        return roleDtos;
    }

    @Override
    public RoleEntity getRoleById(Long role_id) {
        return roleRepository.findById(role_id).get();
    }

    @Override
    public void createRole(RoleEntity role) {
        roleRepository.save(role);
    }

    @Override
    public void updateRole(Long role_id, RoleDtoPost roleDtoPost) {
        RoleEntity role2 = roleRepository.findById(role_id).get();
        role2.setName(roleDtoPost.getName());
        roleRepository.save(role2);
    }

    @Override
    public void deleteRole(Long role_id) {
        roleRepository.deleteById(role_id);  
    }

	@Override
	public void createRoleDto(RoleDtoPost roleDtoPost) {
		RoleEntity roleEntity = new RoleEntity();
        roleEntity.setName(roleDtoPost.getName());

		roleRepository.save(roleEntity);
	}
}

