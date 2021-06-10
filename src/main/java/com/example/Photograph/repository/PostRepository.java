package com.example.Photograph.repository;

import com.example.Photograph.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
    Optional<Post> findByName(String name);
    List<Post> findByUserUsername(String username);
}
