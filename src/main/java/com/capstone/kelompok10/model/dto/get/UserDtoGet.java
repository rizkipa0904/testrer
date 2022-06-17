package com.capstone.kelompok10.model.dto.get;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDtoGet {
    private Long userId;
    private String name;
    private String username;
    private String email;
    private Long phone;
    private String address;
    private String imageUrl;
}
