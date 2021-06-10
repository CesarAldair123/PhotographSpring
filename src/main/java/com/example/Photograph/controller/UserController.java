package com.example.Photograph.controller;

import com.example.Photograph.dto.UserDto;
import com.example.Photograph.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private UserService userService;

    @GetMapping("/{id}")
    private ResponseEntity<UserDto> getUser(@PathVariable int id){
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.findUserById(id));
    }

    @GetMapping()
    private ResponseEntity<List<UserDto>> getUsers(@RequestParam(required = false) String username){
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.findUsers(username));
    }

}
