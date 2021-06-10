package com.example.Photograph.service;

import com.example.Photograph.dto.*;
import com.example.Photograph.exception.DuplicatedUsernameException;
import com.example.Photograph.exception.DuplicatedEmailException;
import com.example.Photograph.exception.InvalidVerificationToken;
import com.example.Photograph.exception.RefreshTokenNotFound;
import com.example.Photograph.model.RefreshToken;
import com.example.Photograph.model.User;
import com.example.Photograph.model.VerificationToken;
import com.example.Photograph.repository.RefreshTokenRepository;
import com.example.Photograph.repository.UserRepository;
import com.example.Photograph.repository.VerificationTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AuthenticationService {

    private UserRepository userRepository;
    private VerificationTokenRepository verificationTokenRepository;
    private RefreshTokenRepository refreshTokenRepository;

    private PasswordEncoder passwordEncoder;

    private MailService mailService;

    private AuthenticationManager authenticationManager;

    private JwtService jwtService;

    @Transactional
    public void signup(SignupRequest signupRequest){
        validateDuplicates(signupRequest);
        User user = User.builder()
                .username(signupRequest.getUsername())
                .email(signupRequest.getEmail())
                .password(passwordEncoder.encode(signupRequest.getPassword()))
                .birthdate(signupRequest.getBirthdate())
                .created(LocalDate.now())
                .enabled(false)
                .build();
        userRepository.save(user);
        String token = generateVerificationToken(user);
        mailService.sendVerificationEmail(user.getUsername(), user.getEmail(), token);
    }

    @Transactional
    public void validateDuplicates(SignupRequest signupRequest){
        if(userRepository.findByUsername(signupRequest.getUsername()).isPresent())
            throw new DuplicatedUsernameException("Username '" + signupRequest.getUsername() + "' is already used");
        if(userRepository.findByEmail(signupRequest.getEmail()).isPresent())
            throw new DuplicatedEmailException("Email '" + signupRequest.getEmail() + "' is already used");
    }

    @Transactional
    public String generateVerificationToken(User user){
        VerificationToken verificationToken =  VerificationToken.builder()
                .id(UUID.randomUUID().toString())
                .user(user)
                .build();
        verificationTokenRepository.save(verificationToken);
        return verificationToken.getId();
    }

    @Transactional
    public void verify(String token) {
        VerificationToken verificationToken = verificationTokenRepository.findById(token)
                .orElseThrow(()-> new InvalidVerificationToken("Token '" + token + "' is invalid"));
        User user = verificationToken.getUser();
        user.setEnabled(true);
        userRepository.save(user);
    }

    public LoginResponse login(LoginRequest loginRequest){
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User)authentication.getPrincipal();
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtService.generateJWT(user.getUsername());
        String refreshToken = generateRefreshToken(user.getUsername());
        return LoginResponse.builder()
                .token(token)
                .expirationDate(jwtService.getExpirationDateFromToken(token))
                .username(user.getUsername())
                .refreshToken(refreshToken)
                .build();
    }

    @Transactional
    public String generateRefreshToken(String username){
        User user = userRepository.findByUsername(username)
                .orElseThrow(()->new UsernameNotFoundException("Username '" + username + "' not found"));
        RefreshToken refreshToken = RefreshToken.builder()
                .id(UUID.randomUUID().toString())
                .user(user)
                .created(new Date())
                .build();
        return refreshTokenRepository.save(refreshToken).getId();
    }

    @Transactional
    public RefreshResponse refresh(RefreshRequest refreshRequest) {
        RefreshToken refreshToken = refreshTokenRepository.findByIdAndUserUsername(refreshRequest.getRefreshToken(), refreshRequest.getUsername())
                .orElseThrow(() -> new RefreshTokenNotFound("Refresh Token not found"));
        String jwtToken = jwtService.generateJWT(refreshToken.getUser().getUsername());
        return RefreshResponse.builder()
                .username(refreshToken.getUser().getUsername())
                .token(jwtToken)
                .expirationDate(jwtService.getExpirationDateFromToken(jwtToken))
                .build();
    }

    @Transactional
    public void logout(LogoutRequest logoutRequest){
        RefreshToken refreshToken = refreshTokenRepository.findById(logoutRequest.getRefreshToken())
                .orElseThrow(()->new RefreshTokenNotFound("Refresh Token not found"));
        refreshTokenRepository.delete(refreshToken);
    }

    @Transactional
    public User getActualUser(){
        String username = ((org.springframework.security.core.userdetails.User)SecurityContextHolder.
                getContext().
                getAuthentication().
                getPrincipal()).getUsername();
        return userRepository.findByUsername(username)
                .orElseThrow(()->new UsernameNotFoundException("User not found"));
    }
}
