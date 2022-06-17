package com.capstone.kelompok10.model.dto.get;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ClassDtoGet {
    private Long classId;
    private Long capacity;
    private String schedule;
    private Long price;
    private Boolean status;
    private String type;
    private String imageUrl;
    private String instructor;
    private String category;
    private String room;
}
