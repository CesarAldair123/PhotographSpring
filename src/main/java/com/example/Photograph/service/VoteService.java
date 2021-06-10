package com.example.Photograph.service;

import com.example.Photograph.exception.PostNotFound;
import com.example.Photograph.model.Post;
import com.example.Photograph.model.User;
import com.example.Photograph.model.Vote;
import com.example.Photograph.repository.PostRepository;
import com.example.Photograph.repository.VoteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class VoteService {

    private VoteRepository voteRepository;
    private AuthenticationService authenticationService;
    private PostRepository postRepository;

    public void upvote(int postId){
        vote(postId, Vote.VoteType.UPVOTE);
    }

    public void downvote(int postId){
        vote(postId, Vote.VoteType.DOWNVOTE);
    }

    private void vote(int postId, Vote.VoteType value){
        User user = authenticationService.getActualUser();
        Post post = postRepository.findById(postId)
                .orElseThrow(()->new PostNotFound("Post with id '" + postId + "' not found"));
        Optional<Vote> voteOptional = voteRepository.findByPostAndUser(post, user);
        if(voteOptional.isPresent()){
            Vote vote = voteOptional.get();
            if(vote.getType() == value) {
                voteRepository.delete(vote);
            }else {
                vote.setType(value);
                voteRepository.save(vote);
            }
        }else{
            Vote vote = Vote.builder()
                    .post(post)
                    .user(user)
                    .type(value)
                    .build();
            voteRepository.save(vote);
        }
    }
}
