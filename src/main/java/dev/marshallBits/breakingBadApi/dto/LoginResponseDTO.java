package dev.marshallBits.breakingBadApi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class LoginResponseDTO {
    private String token;
    @Builder.Default
    private String tokenType = "Bearer";
    private String username;
}
