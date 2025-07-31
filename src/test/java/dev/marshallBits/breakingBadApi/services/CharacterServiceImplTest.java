package dev.marshallBits.breakingBadApi.services;

import dev.marshallBits.breakingBadApi.dto.CharacterDTO;
import dev.marshallBits.breakingBadApi.dto.CreateCharacterDTO;
import dev.marshallBits.breakingBadApi.models.Character;
import dev.marshallBits.breakingBadApi.models.CharacterStatus;
import dev.marshallBits.breakingBadApi.repositories.CharacterRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CharacterServiceImplTest {

    @Mock
    private CharacterRepository characterRepository;

    @InjectMocks
    private CharacterServiceImpl characterService;

    private Character walterWhite;
    private Character jessePinkman;

    @BeforeEach
    void setUp() {
        walterWhite = new Character();
        walterWhite.setId(1L);
        walterWhite.setName("Walter White");
        walterWhite.setOccupation("High School Chemistry Teacher");
        walterWhite.setStatus(CharacterStatus.ALIVE);
        walterWhite.setImageUrl("walter.jpg");

        jessePinkman = new Character();
        jessePinkman.setId(2L);
        jessePinkman.setName("Jesse Pinkman");
        jessePinkman.setOccupation("Meth Dealer");
        jessePinkman.setStatus(CharacterStatus.ALIVE);
        jessePinkman.setImageUrl("jesse.jpg");
    }

    @Test
    @DisplayName("Debe obtener todos los personajes correctamente desde el servicio")
    void shouldFindAllCharacters() {
        List<Character> characters = Arrays.asList(walterWhite, jessePinkman);
        when(characterRepository.findAll()).thenReturn(characters);

        List<CharacterDTO> result = characterService.findAll();

        assertEquals(2, result.size());
        assertEquals("Walter White", result.get(0).getName());
        assertEquals("Jesse Pinkman", result.get(1).getName());
        verify(characterRepository).findAll();
    }

    @Test
    @DisplayName("Debe crear un nuevo personaje correctamente desde el servicio")
    void shouldCreateCharacter() {
        CreateCharacterDTO createDTO = new CreateCharacterDTO("Saul Goodman", "Lawyer", CharacterStatus.ALIVE, "saul.jpg");
        Character savedCharacter = new Character();
        savedCharacter.setId(3L);
        savedCharacter.setName("Saul Goodman");
        savedCharacter.setOccupation("Lawyer");
        savedCharacter.setStatus(CharacterStatus.ALIVE);
        savedCharacter.setImageUrl("saul.jpg");

        when(characterRepository.save(any(Character.class))).thenReturn(savedCharacter);

        CharacterDTO result = characterService.createCharacter(createDTO);

        assertEquals(3L, result.getId());
        assertEquals("Saul Goodman", result.getName());
        assertEquals("Lawyer", result.getOccupation());
        assertEquals(CharacterStatus.ALIVE, result.getStatus());
        verify(characterRepository).save(any(Character.class));
    }

    @Test
    @DisplayName("Debe encontrar un personaje por ID correctamente desde el servicio")
    void shouldFindCharacterById() {
        Long characterId = 1L;
        when(characterRepository.findById(characterId)).thenReturn(Optional.of(walterWhite));

        CharacterDTO result = characterService.findById(characterId);

        assertEquals(1L, result.getId());
        assertEquals("Walter White", result.getName());
        assertEquals("High School Chemistry Teacher", result.getOccupation());
        assertEquals(CharacterStatus.ALIVE, result.getStatus());
        verify(characterRepository).findById(characterId);
    }

    @Test
    @DisplayName("Debe lanzar una excepción cuando el personaje no existe")
    void shouldThrowExceptionWhenCharacterNotFoundById() {
        Long nonExistentId = 999L;
        when(characterRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(
            ResponseStatusException.class,
            () -> characterService.findById(nonExistentId)
        );

        assertTrue(exception.getMessage().contains("Recurso no encontrado"), "La excepción debe incluir el mensaje: 'Recurso no encontrado'");
        verify(characterRepository).findById(nonExistentId);
    }

    @Test
    @DisplayName("Debe actualizar el estado del personaje a DEAD correctamente desde el servicio")
    void shouldUpdateCharacterStatusToDead() {
        Long characterId = 1L;
        Character updatedCharacter = new Character();
        updatedCharacter.setId(1L);
        updatedCharacter.setName("Walter White");
        updatedCharacter.setOccupation("High School Chemistry Teacher");
        updatedCharacter.setStatus(CharacterStatus.DEAD);
        updatedCharacter.setImageUrl("walter.jpg");

        when(characterRepository.findById(characterId)).thenReturn(Optional.of(walterWhite));
        when(characterRepository.save(any(Character.class))).thenReturn(updatedCharacter);

        CharacterDTO result = characterService.updateStatusToDead(characterId);

        assertEquals(1L, result.getId());
        assertEquals("Walter White", result.getName());
        assertEquals(CharacterStatus.DEAD, result.getStatus());
        verify(characterRepository).findById(characterId);
        verify(characterRepository).save(any(Character.class));
    }

    @Test
    @DisplayName("Debe lanzar una excepción al intentar actualizar un personaje inexistente")
    void shouldThrowExceptionWhenUpdatingNonExistentCharacter() {
        Long nonExistentId = 999L;
        when(characterRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(
            ResponseStatusException.class,
            () -> characterService.updateStatusToDead(nonExistentId)
        );

        assertTrue(exception.getMessage().contains("Recurso no encontrado"), "La excepción debe incluir el mensaje: 'Recurso no encontrado'");
        verify(characterRepository).findById(nonExistentId);
        verify(characterRepository, never()).save(any(Character.class));
    }

    @Test
    @DisplayName("Debe actualizar solo el estado del personaje a DEAD sin modificar otros campos")
    void shouldOnlyUpdateStatusToDeadNotOtherFields() {
        Long characterId = 1L;
        String originalName = walterWhite.getName();
        String originalOccupation = walterWhite.getOccupation();
        String originalImageUrl = walterWhite.getImageUrl();

        when(characterRepository.findById(characterId)).thenReturn(Optional.of(walterWhite));
        when(characterRepository.save(any(Character.class))).thenAnswer(invocation -> {
            Character character = invocation.getArgument(0);
            character.setStatus(CharacterStatus.DEAD);
            return character;
        });

        CharacterDTO result = characterService.updateStatusToDead(characterId);

        assertEquals(originalName, result.getName());
        assertEquals(originalOccupation, result.getOccupation());
        assertEquals(originalImageUrl, result.getImageUrl());
        assertEquals(CharacterStatus.DEAD, result.getStatus());
    }
}
