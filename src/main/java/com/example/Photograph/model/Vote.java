package com.example.Photograph.model;

import lombok.*;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Vote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;

    @ManyToOne
    private Post post;

    @ManyToOne
    private User user;

    private VoteType type;

    public enum VoteType{
        UPVOTE, DOWNVOTE;
    }
}
