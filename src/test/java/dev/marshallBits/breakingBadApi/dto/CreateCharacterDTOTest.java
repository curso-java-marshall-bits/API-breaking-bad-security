package dev.marshallBits.breakingBadApi.dto;

import dev.marshallBits.breakingBadApi.models.CharacterStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.Arrays;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class CreateCharacterDTOTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("Debe pasar la validación con datos válidos")
    void shouldPassValidationWithValidData() {
        CreateCharacterDTO dto = new CreateCharacterDTO(
                "Walter White",
                "High School Chemistry Teacher",
                CharacterStatus.ALIVE,
                "walter.jpg"
        );

        Set<ConstraintViolation<CreateCharacterDTO>> validationErrors = validator.validate(dto);

        assertTrue(validationErrors.isEmpty(), "No debería haber errores de validación con datos válidos");
    }

    @Test
    @DisplayName("Debe fallar la validación cuando el nombre está vacío")
    void shouldFailValidationWhenNameIsBlank() {
        CreateCharacterDTO dto = new CreateCharacterDTO(
                "",
                "High School Chemistry Teacher",
                CharacterStatus.ALIVE,
                "walter.jpg"
        );

        Set<ConstraintViolation<CreateCharacterDTO>> validationErrors = validator.validate(dto);

        assertFalse(validationErrors.isEmpty(), "Debería haber errores de validación cuando el nombre está vacío");
        assertTrue(validationErrors.stream().anyMatch(v -> v.getPropertyPath().toString().equals("name")),
                "Debería haber un error de validación en el campo 'name'");
    }

    @Test
    @DisplayName("Debe fallar la validación cuando el nombre es nulo")
    void shouldFailValidationWhenNameIsNull() {
        CreateCharacterDTO dto = new CreateCharacterDTO(
                null,
                "High School Chemistry Teacher",
                CharacterStatus.ALIVE,
                "walter.jpg"
        );

        Set<ConstraintViolation<CreateCharacterDTO>> validationErrors = validator.validate(dto);

        assertFalse(validationErrors.isEmpty(), "Debería haber errores de validación cuando el nombre es nulo");
        assertTrue(validationErrors.stream().anyMatch(v -> v.getPropertyPath().toString().equals("name")),
                "Debería haber un error de validación en el campo 'name'");
    }

    @Test
    @DisplayName("Debe fallar la validación cuando la ocupación está vacía")
    void shouldFailValidationWhenOccupationIsBlank() {
        CreateCharacterDTO dto = new CreateCharacterDTO(
                "Walter White",
                "",
                CharacterStatus.ALIVE,
                "walter.jpg"
        );

        Set<ConstraintViolation<CreateCharacterDTO>> validationErrors = validator.validate(dto);

        assertFalse(validationErrors.isEmpty(), "Debería haber errores de validación cuando la ocupación está vacía");
        assertTrue(validationErrors.stream().anyMatch(v -> v.getPropertyPath().toString().equals("occupation")),
                "Debería haber un error de validación en el campo 'occupation'");
    }

    @Test
    @DisplayName("Debe fallar la validación cuando el estado es nulo")
    void shouldFailValidationWhenStatusIsNull() {
        CreateCharacterDTO dto = new CreateCharacterDTO(
                "Walter White",
                "High School Chemistry Teacher",
                null,
                "walter.jpg"
        );

        Set<ConstraintViolation<CreateCharacterDTO>> validationErrors = validator.validate(dto);

        assertFalse(validationErrors.isEmpty(), "Debería haber errores de validación cuando el status es nulo");
        assertTrue(validationErrors.stream().anyMatch(v -> v.getPropertyPath().toString().equals("status")),
                "Debería haber un error de validación en el campo 'status'");
    }

    @Test
    @DisplayName("Debe fallar la validación cuando el nombre es demasiado largo")
    void shouldFailValidationWhenNameIsTooLong() {
        String longName = "A".repeat(101); // nombre muy largo (más de 100 caracteres)
        CreateCharacterDTO dto = new CreateCharacterDTO(
                longName,
                "High School Chemistry Teacher",
                CharacterStatus.ALIVE,
                "walter.jpg"
        );

        Set<ConstraintViolation<CreateCharacterDTO>> validationErrors = validator.validate(dto);

        assertFalse(validationErrors.isEmpty(), "Debería haber errores de validación cuando el nombre es más largo de 100 caracteres");
        assertTrue(validationErrors.stream().anyMatch(v -> v.getPropertyPath().toString().equals("name")),
                "Debería haber un error de validación en el campo 'name'");
    }

    @Test
    @DisplayName("Debe fallar la validación cuando la ocupación es demasiado larga")
    void shouldFailValidationWhenOccupationIsTooLong() {
        String longOccupation = "B".repeat(201);
        CreateCharacterDTO dto = new CreateCharacterDTO(
                "Walter White",
                longOccupation,
                CharacterStatus.ALIVE,
                "walter.jpg"
        );

        Set<ConstraintViolation<CreateCharacterDTO>> validationErrors = validator.validate(dto);

        assertFalse(validationErrors.isEmpty(), "Debería haber errores de validación cuando la ocupación es más de 200 caracteres");
        assertTrue(validationErrors.stream().anyMatch(v -> v.getPropertyPath().toString().equals("occupation")),
                "Debería haber un error de validación en el campo 'occupation'");
    }

    @Test
    @DisplayName("Debe pasar la validación con longitudes límite válidas")
    void shouldPassValidationWithValidBoundaryLengths() {

        String maxLengthName = "A".repeat(100);
        String maxLengthOccupation = "B".repeat(200);

        CreateCharacterDTO dto = new CreateCharacterDTO(
                maxLengthName,
                maxLengthOccupation,
                CharacterStatus.ALIVE,
                "walter.jpg"
        );

        Set<ConstraintViolation<CreateCharacterDTO>> validationErrors = validator.validate(dto);

        assertTrue(validationErrors.isEmpty(), "No debería haber errores de validación con longitudes límite válidas");
    }

    @Test
    @DisplayName("Debe pasar la validación cuando los campos opcionales son nulos")
    void shouldPassValidationWhenOptionalFieldsAreNull() {

        CreateCharacterDTO dto = new CreateCharacterDTO(
                "Walter White",
                "High School Chemistry Teacher",
                CharacterStatus.ALIVE,
                null
        );

        Set<ConstraintViolation<CreateCharacterDTO>> validationErrors = validator.validate(dto);

        assertTrue(validationErrors.isEmpty(), "No debería haber errores de validación cuando los campos opcionales son nulos");
    }
}
