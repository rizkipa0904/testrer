package com.capstone.kelompok10;

import java.util.ArrayList;

import com.capstone.kelompok10.model.entity.RoleEntity;
import com.capstone.kelompok10.model.entity.UserEntity;
import com.capstone.kelompok10.service.interfaces.RoleService;
import com.capstone.kelompok10.service.interfaces.UserService;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class Kelompok10Application {

	public static void main(String[] args) {
		SpringApplication.run(Kelompok10Application.class, args);
	}

	@Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

	@Bean
	public WebMvcConfigurer corsConfigurer(){
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry){
				registry.addMapping("/auth/**").allowedOrigins("https://www.api.rafdev.my.id");
			}
		};
	}

	@Bean
	CommandLineRunner runner(UserService userService, RoleService roleService){
		return args -> {
			roleService.createRole(new RoleEntity(1L, "ROLE_USER", null, null));
			roleService.createRole(new RoleEntity(2L, "ROLE_ADMIN", null, null));
			roleService.createRole(new RoleEntity(3L, "ROLE_SUPER_ADMIN", null, null));

			userService.createUser(new UserEntity(4L, "Back End", "backend", "susahbenerinipassword", "backend@email.com", 123456L, "Kolong Jembatan",null, null, null, null, new ArrayList<>(), null));
			userService.createUser(new UserEntity(1L, "Manager", "manager", "buatpasswordyangsusah", "manager@email.com", 123456L, "Valhalla",null, null, null, null, new ArrayList<>(), null));
			userService.createUser(new UserEntity(2L, "Admin", "admin", "yanginijugasusah", "admin@email.com", 123456L, "Asgard",null, null, null, null, new ArrayList<>(), null));
			userService.createUser(new UserEntity(3L, "User", "rakha", "yanginigampang", "user@email.com", 123456L, "Midgrad",null, null, null, null, new ArrayList<>(), null));

			userService.addRoleToUser("backend", "ROLE_SUPER_ADMIN");
			userService.addRoleToUser("manager", "ROLE_SUPER_ADMIN");
			userService.addRoleToUser("admin", "ROLE_ADMIN");
			userService.addRoleToUser("rakha", "ROLE_USER");
		};
	}

}
