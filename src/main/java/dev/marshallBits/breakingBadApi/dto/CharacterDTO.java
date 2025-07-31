package dev.marshallBits.breakingBadApi.dto;

import dev.marshallBits.breakingBadApi.models.CharacterStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
/*
 Data Transfer Object para Character.
 Aunque representa exactamente la misma estructura que la entidad Character,
 se utiliza para separar la lógica de negocio de la lógica de presentación.
 Si en algún momento queremos ocultar algunos campos de la entidad Character que
 mandemos al cliente, podemos hacerlo fácilmente sin afectar la entidad original.
*/
public class CharacterDTO {
    private Long id;
    private String name;
    private String occupation;
    private CharacterStatus status;
    private String imageUrl;
}
