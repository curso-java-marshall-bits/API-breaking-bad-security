package dev.marshallBits.breakingBadApi.repositories;

import dev.marshallBits.breakingBadApi.models.Character;
import dev.marshallBits.breakingBadApi.models.CharacterStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface CharacterRepository extends JpaRepository<Character, Long> {

    List<Character> findByOccupationContainingIgnoreCase(String occupation);

    List<Character> findByStatus(CharacterStatus status);

    boolean existsByNameIgnoreCase(String name);
}
