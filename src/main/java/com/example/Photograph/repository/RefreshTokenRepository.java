package com.example.Photograph.repository;

import com.example.Photograph.model.RefreshToken;
import com.example.Photograph.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {
    Optional<RefreshToken> findByIdAndUserUsername(String id, String username);
    List<RefreshTokenRepository> findByUser(User user);
}
