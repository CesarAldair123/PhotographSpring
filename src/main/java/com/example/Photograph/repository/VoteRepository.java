package com.example.Photograph.repository;

import com.example.Photograph.model.Post;
import com.example.Photograph.model.User;
import com.example.Photograph.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Integer> {
    Optional<Vote> findByPostAndUser(Post post, User user);
    @Query("SELECT COUNT(v) FROM Vote v WHERE v.post = ?1 AND v.type = 0")
    int findUpVoteCount(Post post);
    @Query("SELECT COUNT(v) FROM Vote v WHERE v.post = ?1 AND v.type = 1")
    int findDownVoteCount(Post post);
}
