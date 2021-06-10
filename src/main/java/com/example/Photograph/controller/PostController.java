package com.example.Photograph.controller;

import com.example.Photograph.dto.PostDto;
import com.example.Photograph.dto.ResponseDto;
import com.example.Photograph.service.PostService;
import com.example.Photograph.service.VoteService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/post")
@AllArgsConstructor
public class PostController {

    private PostService postService;
    private VoteService voteService;

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> get(@PathVariable int id, HttpServletRequest request){
        return ResponseEntity.ok(postService.getById(id, request));
    }

    @GetMapping()
    public ResponseEntity<List<PostDto>> getAll(@RequestParam(required = false, defaultValue = "1") int page
            , HttpServletRequest request){
        return ResponseEntity.ok(postService.getAll(page,request));
    }

    @PostMapping()
    public ResponseEntity<ResponseDto> upload(@RequestPart MultipartFile file,
                                              @RequestPart String name,
                                              @RequestPart String description){
        postService.save(file, name, description);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.builder()
                        .message("File Upload Correctly")
                        .status(HttpStatus.OK.value())
                        .build());
    }

    @PostMapping("/{id}/upvote")
    public ResponseEntity<ResponseDto> upvote(@PathVariable int id){
        voteService.upvote(id);
        return ResponseEntity.ok(new ResponseDto("Vote Correctly", HttpStatus.OK.value()));
    }

    @PostMapping("/{id}/downvote")
    public ResponseEntity<ResponseDto> downvote(@PathVariable int id){
        voteService.downvote(id);
        return ResponseEntity.ok(new ResponseDto("Vote Correctly", HttpStatus.OK.value()));
    }
}
