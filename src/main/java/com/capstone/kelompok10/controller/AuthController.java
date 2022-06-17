package com.capstone.kelompok10.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.capstone.kelompok10.model.entity.RoleEntity;
import com.capstone.kelompok10.model.entity.UserEntity;
import com.capstone.kelompok10.model.payload.ForgotPassword;
import com.capstone.kelompok10.model.payload.RegistrationRequest;
import com.capstone.kelompok10.model.payload.ResendToken;
import com.capstone.kelompok10.service.interfaces.RegisterService;
import com.capstone.kelompok10.service.interfaces.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private RegisterService registerService;

    @Autowired
    private UserService userService;

    @GetMapping("/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                String refresh_token = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refresh_token);
                String username = decodedJWT.getSubject();
                UserEntity user = userService.getUser(username);
                String access_token = JWT.create()
                        .withSubject(user.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 30 * 60 * 1000))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles", user.getRoles().stream().map(RoleEntity::getName).collect(Collectors.toList()))
                        .sign(algorithm);
                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token", access_token);
                tokens.put("refresh_token", refresh_token);
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);
            } catch (Exception e) {
                response.setHeader("error", e.getMessage());
                response.setStatus(FORBIDDEN.value());
                Map<String, String> error = new HashMap<>();
                error.put("error_message", e.getMessage());
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }
        } else {
            throw new RuntimeException("Refresh token is missing");
        }
    }

    @PostMapping("/register")
    public String register(@RequestBody RegistrationRequest register){
        return registerService.register(register);
    }

    @GetMapping("/confirm")
    public String verifyUser(@RequestParam("token") String token){
        if (registerService.confirmToken(token)){
            return "Verify Success";
        }else{
            return "Verify Fail";
        }
    }

    @PostMapping("/resend")
    public String resend(@RequestBody ResendToken resendToken){
        return registerService.resendToken(resendToken);
    }

    @PostMapping("/forgot")
    public String resetPass(@RequestBody ResendToken reset){
        return registerService.resetPassword(reset);
    }

    @PostMapping("/reset")
    public String resetPassword(@RequestParam("token") String token, @RequestBody ForgotPassword forgotPassword){
        if (registerService.forgotPassword(token, forgotPassword)){
            return "Password Changed";
        }else{
            return "Password not Changed";
        }
    }
}
