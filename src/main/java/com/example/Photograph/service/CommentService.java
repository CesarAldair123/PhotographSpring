package com.example.Photograph.service;

import com.example.Photograph.dto.CommentDto;
import com.example.Photograph.dto.CommentRequest;
import com.example.Photograph.exception.PostNotFound;
import com.example.Photograph.mapper.CommentMapper;
import com.example.Photograph.model.Comment;
import com.example.Photograph.repository.CommentRepository;
import com.example.Photograph.repository.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CommentService {

    private AuthenticationService authenticationService;
    private CommentRepository commentRepository;
    private PostRepository postRepository;
    private CommentMapper commentMapper;

    public void saveComment(CommentRequest commentRequest){
        Comment comment = Comment.builder()
                .user(authenticationService.getActualUser())
                .created(new Date())
                .post(postRepository.findById(commentRequest.getPostId())
                        .orElseThrow(()->new PostNotFound("Post Not found")))
                .comment(commentRequest.getComment())
                .build();
        commentRepository.save(comment);
    }

    public List<CommentDto> getAllComments(HttpServletRequest request){
        Locale lang = request.getLocale();
        return commentRepository.findAll()
                .stream()
                .map((comment -> commentMapper.commentToDto(comment, lang)))
                .collect(Collectors.toList());
    }
}
