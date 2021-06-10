package com.example.Photograph.controller;

import com.example.Photograph.dto.*;
import com.example.Photograph.service.AuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping("/api/auth")
public class AuthenticationController {

    private AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<ResponseDto> signup(@RequestBody @Valid SignupRequest signupRequest){
        authenticationService.signup(signupRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseDto.builder()
                        .message("Registered Correctly")
                        .status(HttpStatus.CREATED.value())
                        .build());
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest){
        return ResponseEntity.status(HttpStatus.OK)
        .body(authenticationService.login(loginRequest));
    }

    @GetMapping("/verify/{token}")
    public String verify(@PathVariable String token){
        authenticationService.verify(token);
        return "Email Verified";
    }

    @PostMapping("/refresh")
    public ResponseEntity<RefreshResponse> refresh(@RequestBody RefreshRequest refreshRequest){
        return ResponseEntity.status(HttpStatus.OK)
                .body(authenticationService.refresh(refreshRequest));
    }

    @PostMapping("/logout")
    public ResponseEntity<ResponseDto> logout(@RequestBody LogoutRequest logoutRequest){
        authenticationService.logout(logoutRequest);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.builder()
                        .status(HttpStatus.OK.value())
                        .message("User Logout Correctly")
                        .build());
    }

}
