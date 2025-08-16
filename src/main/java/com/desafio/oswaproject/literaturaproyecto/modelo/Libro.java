package com.desafio.oswaproject.literaturaproyecto.modelo;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idLibro;
    private String titulo;
    @ElementCollection
    private List<String> idomas;
    private Integer noDescargas;
    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinTable(
            name = "libro_autor",
            joinColumns = @JoinColumn(name = "id_libro"),
            inverseJoinColumns = @JoinColumn(name = "id_autor")
    )
    private List<Autor> autores;


    public Libro(){}

    public Libro (LibroDTO dto){
        this.titulo = dto.titulo();
        this.noDescargas = dto.noDescargas();
        this.idomas = dto.idiomas(); // lista de strings por ahora
        this.autores = dto.autores().stream()
                .map(Autor::new) // convertimos cada AutorDTO en Autor entidad
                .toList();
    }

    public Long getIdLibro() {
        return idLibro;
    }

    public void setIdLibro(Long idLibro) {
        this.idLibro = idLibro;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<String> getIdomas() {
        return idomas;
    }

    public void setIdomas(List<String> idomas) {
        this.idomas = idomas;
    }

    public Integer getNoDescargas() {
        return noDescargas;
    }

    public void setNoDescargas(Integer noDescargas) {
        this.noDescargas = noDescargas;
    }

    public List<Autor> getAutores() {
        return autores;
    }

    public void setAutores(List<Autor> autores) {
        this.autores = autores;
    }
}
