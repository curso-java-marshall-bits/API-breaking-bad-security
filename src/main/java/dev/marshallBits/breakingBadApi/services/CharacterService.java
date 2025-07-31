package dev.marshallBits.breakingBadApi.services;

import dev.marshallBits.breakingBadApi.dto.CharacterDTO;
import dev.marshallBits.breakingBadApi.dto.CreateCharacterDTO;

import java.util.List;

public interface CharacterService {

    List<CharacterDTO> findAll();

    CharacterDTO createCharacter(CreateCharacterDTO createCharacterDTO);

    CharacterDTO findById(Long id);

    CharacterDTO updateStatusToDead(Long id);

    CharacterDTO updateCharacter(Long id, CreateCharacterDTO updateCharacterDTO);
}
