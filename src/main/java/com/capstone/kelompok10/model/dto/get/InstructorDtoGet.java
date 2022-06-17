package com.capstone.kelompok10.model.dto.get;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class InstructorDtoGet {
    private Long instructorId;
    private String name;
    private Long contact;
    private String imageUrl;
}

