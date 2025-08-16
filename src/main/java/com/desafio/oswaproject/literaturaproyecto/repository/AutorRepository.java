package com.desafio.oswaproject.literaturaproyecto.repository;

import com.desafio.oswaproject.literaturaproyecto.modelo.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AutorRepository extends JpaRepository <Autor, Long>{

    @Query("SELECT a FROM Autor a")
    List<Autor> autoresRegistrados();

    @Query("SELECT a FROM Autor a WHERE :fecha BETWEEN a.fechaNacimiento AND a.fechaMuerte")
    List<Autor> listaDeAutoresEntreAno(@Param("fecha") Integer fecha);
}
