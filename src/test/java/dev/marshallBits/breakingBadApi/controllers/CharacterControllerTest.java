package dev.marshallBits.breakingBadApi.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.marshallBits.breakingBadApi.dto.CreateCharacterDTO;
import dev.marshallBits.breakingBadApi.models.CharacterStatus;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CharacterControllerTest {
    // MockMVC (Modelo Vista Controlador)
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @Order(1)
    @DisplayName("GET: Recibimos 10 characters en api/caracters")
    void getAllCharacters() throws Exception {
       mockMvc.perform(get("/api/characters"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(10));
    }

    @Test
    @Order(2)
    @DisplayName("POST: un nuevo character funciona correctamente en api/characters")
    void postNewCharacter() throws Exception{
        CreateCharacterDTO saul = CreateCharacterDTO
                .builder()
                .name("Saul Goodman")
                .occupation("Lawyer")
                .status(CharacterStatus.ALIVE)
                .build();

        mockMvc.perform(post("/api/characters")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(saul))
        )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Saul Goodman"))
                .andExpect(jsonPath("$.occupation").value("Lawyer"));


    }

    @Test
    @Order(3)
    @DisplayName("Get con id 4 nos muestra a Hank Schrader")
    void getHankById() throws Exception {
        mockMvc.perform(get("/api/characters/4"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Hank Schrader"))
                .andExpect(jsonPath("$.status").value("ALIVE"))
                .andExpect(jsonPath("$.occupation").value("Agente DEA"));
    }

    @Test
    @Order(4)
    @DisplayName("PATCH status con id 4 nos muestra a Hank Schrader como DEAD")
    void patchHankById() throws Exception {
        mockMvc.perform(patch("/api/characters/4/status"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("DEAD"));
    }

    @Test
    @Order(5)
    @DisplayName("PUT: Hank ahora es Wilson")
    void putHank() throws Exception{
        CreateCharacterDTO wilson = CreateCharacterDTO
                .builder()
                .name("Wilson Thompson")
                .occupation("Farmer")
                .status(CharacterStatus.ALIVE)
                .build();

        mockMvc.perform(put("/api/characters/4")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(wilson))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Wilson Thompson"))
                .andExpect(jsonPath("$.occupation").value("Farmer"))
                .andExpect(jsonPath("$.status").value("ALIVE"));
    }

    @Test
    @Order(6)
    @DisplayName("PUT muestra un error con name vacío")
    void putError() throws Exception{
        CreateCharacterDTO wilson = CreateCharacterDTO
                .builder()
                .name("") // creamos el personaje de forma incorrecta
                .occupation("Farmer")
                .status(CharacterStatus.ALIVE)
                .build();

        mockMvc.perform(put("/api/characters/4")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(wilson))
                )
                .andExpect(status().is4xxClientError());
    }
}
