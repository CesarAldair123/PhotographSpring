package com.example.Photograph.mapper;

import com.example.Photograph.dto.PostDto;
import com.example.Photograph.model.Comment;
import com.example.Photograph.model.Post;
import com.example.Photograph.model.User;
import com.example.Photograph.model.Vote;
import com.example.Photograph.repository.VoteRepository;
import com.example.Photograph.service.AuthenticationService;
import com.github.marlonlom.utilities.timeago.TimeAgo;
import com.github.marlonlom.utilities.timeago.TimeAgoMessages;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Locale;
import java.util.Optional;

@Mapper(componentModel = "spring")
public abstract class PostMapper {

    @Autowired
    private VoteRepository voteRepository;
    @Autowired
    private AuthenticationService authenticationService;

    @Mapping(target="creator", source = "post.user.username")
    @Mapping(target="created", expression = "java(getTimeAgo(post, locale))")
    @Mapping(target="voteCount", expression="java(getVoteCount(post))")
    @Mapping(target="userVote", expression="java(userVote(post))")
    public abstract PostDto postToDto(Post post, Locale locale);

    public int getVoteCount(Post post){
        return voteRepository.findUpVoteCount(post) - voteRepository.findDownVoteCount(post);
    }

    public int userVote(Post post){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken){
            return 0;
        }
        User user = authenticationService.getActualUser();
        Optional<Vote> voteOptional = voteRepository.findByPostAndUser(post, user);
        if(!voteOptional.isPresent()) return 0;
        Vote vote = voteOptional.get();
        return vote.getType() == Vote.VoteType.UPVOTE ? 1 : -1;
    }

    public String getTimeAgo(Post post, Locale locale){
        TimeAgoMessages messages = new TimeAgoMessages.Builder().withLocale(locale).build();
        return TimeAgo.using(post.getCreated().getTime(), messages);
    }
}
