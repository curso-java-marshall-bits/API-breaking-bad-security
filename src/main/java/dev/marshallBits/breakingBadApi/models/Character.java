package dev.marshallBits.breakingBadApi.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "characters") // si no ponemos el nombre, JPA usará "character" por defecto, en este caso queremos que se llame "characters"
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Character {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false) // nullable = false significa que este campo no puede ser nulo, así que es obligatorio
    private String name;

    @Column(nullable = false)
    private String occupation;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CharacterStatus status; // vivo o muerto

    private String imageUrl;
}
