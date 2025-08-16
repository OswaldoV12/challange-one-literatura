package com.desafio.oswaproject.literaturaproyecto.repository;

import com.desafio.oswaproject.literaturaproyecto.modelo.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LibroRepository extends JpaRepository <Libro, Long>{

    @Query("SELECT l FROM Libro l")
    List<Libro> librosRegistrados();

    @Query("SELECT l FROM Libro l WHERE :idioma MEMBER OF l.idomas")
    List<Libro> librosPorIdioma(@Param("idioma") String idioma);

}
