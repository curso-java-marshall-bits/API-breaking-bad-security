package dev.marshallBits.breakingBadApi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserDTO {

    @Size(max = 100)
    @NotNull
    @NotBlank(message = "El username no puede estar vacío")
    private String username;

    @NotNull
    @NotBlank
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z]).{6,}$", message = "La contraseña debe ser de al menos 6 caracteres e incluir minúsculas y mayúsculas")
    private String password;
}
