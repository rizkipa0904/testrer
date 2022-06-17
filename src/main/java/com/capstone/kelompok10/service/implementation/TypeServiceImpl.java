package com.capstone.kelompok10.service.implementation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.capstone.kelompok10.model.dto.get.TypeDtoGet;
import com.capstone.kelompok10.model.dto.post.TypeDtoPost;
import com.capstone.kelompok10.model.entity.TypeEntity;
import com.capstone.kelompok10.repository.TypeRepository;
import com.capstone.kelompok10.service.interfaces.TypeService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TypeServiceImpl implements TypeService {
    TypeRepository typeRepository;

    @Autowired
    public TypeServiceImpl(TypeRepository typeRepository){
        this.typeRepository = typeRepository;
    }

    @Override
    public List<TypeEntity> findAll() {
        List<TypeEntity> type = new ArrayList<>();
        typeRepository.findAll().forEach(type::add);
        return type;
    }
    
    @Override
    public Page<TypeEntity> findAllPagination(int offset, int pageSize) {
        Page<TypeEntity> type = typeRepository.findAll(PageRequest.of(offset, pageSize));
        return type;
    }

    @Override
    public Page<TypeEntity> findAllPaginationSort(int offset, int pageSize, String field){
        Page<TypeEntity> type = typeRepository.findAll(PageRequest.of(offset, pageSize).withSort(Sort.by(field)));
        return type;
    }

    @Override
    public List<TypeDtoGet> findAllDto() {
        List<TypeEntity> types = typeRepository.findAll();
        List<TypeDtoGet> typeDtos = new ArrayList<>();
        
        types.forEach(isi ->{
            TypeDtoGet dto = new TypeDtoGet();
            dto.setTypeId(isi.getTypeId());
            dto.setName(isi.getName());

            typeDtos.add(dto);
        });
        return typeDtos;
    }

    @Override
    public TypeEntity getTypeById(Long type_id) {
        return typeRepository.findById(type_id).get();
    }

    @Override
    public void createType(TypeEntity type) {
        typeRepository.save(type);
    }

    @Override
    public void updateType(Long type_id, TypeDtoPost typeDtoPost) {
        TypeEntity type2 = typeRepository.findById(type_id).get();
        type2.setName(typeDtoPost.getName());

        typeRepository.save(type2);
    }

    @Override
    public void deleteType(Long type_id) {
        typeRepository.deleteById(type_id);
    }

    @Override
    public void createTypeDto(TypeDtoPost typeDtoPost) {
        TypeEntity typeEntity = new TypeEntity();
        typeEntity.setName(typeDtoPost.getName());

        typeRepository.save(typeEntity);
    }
}
