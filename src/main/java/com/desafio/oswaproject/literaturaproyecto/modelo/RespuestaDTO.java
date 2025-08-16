package com.desafio.oswaproject.literaturaproyecto.modelo;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record RespuestaDTO(
       @JsonAlias("results") List<LibroDTO> resultado
) {
}
