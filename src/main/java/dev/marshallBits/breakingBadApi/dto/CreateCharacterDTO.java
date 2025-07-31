package dev.marshallBits.breakingBadApi.dto;

import dev.marshallBits.breakingBadApi.models.CharacterStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateCharacterDTO {

    @Size(max = 100)
    @NotNull
    @NotBlank
    private String name;

    @Size(max = 200)
    @NotBlank
    private String occupation;

    @NotNull
    private CharacterStatus status;

    private String imageUrl;
}
