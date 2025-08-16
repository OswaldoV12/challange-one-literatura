package com.desafio.oswaproject.literaturaproyecto.modelo;

import jakarta.persistence.*;

import javax.print.attribute.standard.MediaSize;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAutor;
    private String nombre;
    private Integer fechaNacimiento;
    private Integer fechaMuerte;
    @ManyToMany(mappedBy = "autores")
    private List<Libro> libros;

    public Autor(){}

    public Autor(AutorDTO dto) {
        this.nombre = dto.nombre();
        this.fechaNacimiento = dto.fechaNacimiento();
        this.fechaMuerte = dto.fechaMuerte();
    }

    public Long getIdAutor() {
        return idAutor;
    }

    public void setIdAutor(Long idAutor) {
        this.idAutor = idAutor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Integer fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Integer getFechaMuerte() {
        return fechaMuerte;
    }

    public void setFechaMuerte(Integer fechaMuerte) {
        this.fechaMuerte = fechaMuerte;
    }

    public List<Libro> getLibros() {
        return libros;
    }

    public void setLibros(List<Libro> libros) {
        this.libros = libros;
    }
}
