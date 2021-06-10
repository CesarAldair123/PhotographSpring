package com.example.Photograph.service;

import com.example.Photograph.dto.PostDto;
import com.example.Photograph.exception.PostNotFound;
import com.example.Photograph.mapper.PostMapper;
import com.example.Photograph.model.Post;
import com.example.Photograph.model.User;
import com.example.Photograph.repository.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PostService {

    private AuthenticationService authenticationService;
    private FileService fileService;

    private PostRepository postRepository;

    private PostMapper postMapper;

    public void save(MultipartFile file, String name, String description){
        String fileName = fileService.upload(file);
        User user = authenticationService.getActualUser();
        Post post = Post.builder()
                .user(user)
                .name(name)
                .description(description)
                .created(new Date())
                .image(fileName)
                .build();
        postRepository.save(post);
    }

    public List<PostDto> getAll(int page, HttpServletRequest request){
        Locale locale = request.getLocale();
        int first = (page - 1) * 10;
        int last = page * 10;
        Pageable pageable = PageRequest.of(first, last);
        return postRepository.findAll(pageable)
                .stream()
                .map((post)->postMapper.postToDto(post, locale))
                .collect(Collectors.toList());
    }

    public PostDto getById(int id, HttpServletRequest request) {
        Locale locale = request.getLocale();
        Post post = postRepository.findById(id)
                .orElseThrow(()->new PostNotFound("Post with id '" + id + "' not found"));
        return postMapper.postToDto(post, locale);
    }
}
