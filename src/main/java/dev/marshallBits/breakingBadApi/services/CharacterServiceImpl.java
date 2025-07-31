package dev.marshallBits.breakingBadApi.services;

import dev.marshallBits.breakingBadApi.dto.CharacterDTO;
import dev.marshallBits.breakingBadApi.dto.CreateCharacterDTO;
import dev.marshallBits.breakingBadApi.models.Character;
import dev.marshallBits.breakingBadApi.models.CharacterStatus;
import dev.marshallBits.breakingBadApi.repositories.CharacterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CharacterServiceImpl implements CharacterService {

    @Autowired
    private CharacterRepository characterRepository;

    @Override
    public List<CharacterDTO> findAll() {
        return characterRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CharacterDTO createCharacter(CreateCharacterDTO createCharacterDTO) {
        Character character = convertToEntity(createCharacterDTO);
        character = characterRepository.save(character);
        return convertToDTO(character);
    }

    // TODO: Obtener personaje por ID
    @Override
    public CharacterDTO findById(Long id) {
        Character foundCharacter = characterRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Recurso no encontrado"));
        return convertToDTO(foundCharacter);
    }

    // TODO: Cambiar estado de Alive a Dead
    @Override
    public CharacterDTO updateStatusToDead(Long id) {
        Character foundCharacter = characterRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Recurso no encontrado"));
        foundCharacter.setStatus(CharacterStatus.DEAD);
        characterRepository.save(foundCharacter);

        return convertToDTO(foundCharacter);
    }

    public CharacterDTO updateCharacter(Long id, CreateCharacterDTO updateCharacterDTO) {
        Character foundCharacter = characterRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Recurso no encontrado"));

        foundCharacter.setName(updateCharacterDTO.getName());
        foundCharacter.setOccupation(updateCharacterDTO.getOccupation());
        foundCharacter.setStatus(updateCharacterDTO.getStatus());
        foundCharacter.setImageUrl(updateCharacterDTO.getImageUrl());

        characterRepository.save(foundCharacter);
        return convertToDTO(foundCharacter);
    }

    private CharacterDTO convertToDTO(Character character) {
        return new CharacterDTO(
                character.getId(),
                character.getName(),
                character.getOccupation(),
                character.getStatus(),
                character.getImageUrl()
        );
    }

    private Character convertToEntity(CreateCharacterDTO dto) {
        Character character = new Character();
        character.setName(dto.getName());
        character.setOccupation(dto.getOccupation());
        character.setStatus(dto.getStatus());
        character.setImageUrl(dto.getImageUrl());
        return character;
    }
}
