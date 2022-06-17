package com.capstone.kelompok10.model.dto.post;

import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ClassDtoPost {
    private Boolean status;
    private Long capacity;
    private Date schedule;
    private Long price;
    private String imageUrl;
    private Long instructorId;
    private Long categoryId;
    private Long roomId;
    private Long typeId;
}
