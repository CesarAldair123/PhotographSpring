package com.example.Photograph.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostDto {
    private int id;
    private String name;
    private String description;
    private String creator;
    private String created;
    private String image;
    private int voteCount;
    private int userVote;
}
