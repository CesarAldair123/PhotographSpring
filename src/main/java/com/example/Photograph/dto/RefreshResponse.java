package com.example.Photograph.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class RefreshResponse {
    private String username;
    private String token;
    private Date expirationDate;
}
