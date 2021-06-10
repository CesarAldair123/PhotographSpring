package com.example.Photograph.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RefreshRequest {
    private String refreshToken;
    private String username;
}
