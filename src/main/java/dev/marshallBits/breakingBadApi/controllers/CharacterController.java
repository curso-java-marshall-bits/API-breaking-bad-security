package dev.marshallBits.breakingBadApi.controllers;

import dev.marshallBits.breakingBadApi.dto.CharacterDTO;
import dev.marshallBits.breakingBadApi.dto.CreateCharacterDTO;
import dev.marshallBits.breakingBadApi.services.CharacterServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/characters")
public class CharacterController {

    @Autowired
    private CharacterServiceImpl characterService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CharacterDTO> getAllCharacters() {
        return characterService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CharacterDTO createCharacter(@Valid @RequestBody CreateCharacterDTO createCharacterDTO) {
        return characterService.createCharacter(createCharacterDTO);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CharacterDTO getCharacterById(@PathVariable Long id) {
        return characterService.findById(id);
    }

    @PatchMapping("/{id}/status")
    @ResponseStatus(HttpStatus.OK)
    public CharacterDTO updateCharacterStatus(@PathVariable Long id) {
        return characterService.updateStatusToDead(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CharacterDTO updateCharacter(
            @PathVariable Long id,
            @Valid @RequestBody CreateCharacterDTO updateCharacterDTO
    ) {
        return characterService.updateCharacter(id, updateCharacterDTO);
    }
}
