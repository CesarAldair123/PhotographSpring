package com.example.Photograph.controller;

import com.example.Photograph.dto.CommentDto;
import com.example.Photograph.dto.CommentRequest;
import com.example.Photograph.dto.ResponseDto;
import com.example.Photograph.service.CommentService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Locale;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/api/comment")
public class CommentController {

    private CommentService commentService;

    @PostMapping()
    public ResponseEntity<ResponseDto> save(@RequestBody CommentRequest commentRequest){
        commentService.saveComment(commentRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDto("Comment Created", HttpStatus.CREATED.value()));
    }

    @GetMapping()
    public ResponseEntity<List<CommentDto>> getAll(HttpServletRequest request){
        return ResponseEntity.ok(commentService.getAllComments(request));
    }
}
