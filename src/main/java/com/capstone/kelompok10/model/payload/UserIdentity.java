package com.capstone.kelompok10.model.payload;

import lombok.Data;

@Data
public class UserIdentity {
    private Long user_id;
    private String name;
    private String username;
    private String password;
    private String email;
    private Long phone;
}
