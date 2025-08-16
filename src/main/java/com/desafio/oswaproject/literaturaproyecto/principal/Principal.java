package com.desafio.oswaproject.literaturaproyecto.principal;

import com.desafio.oswaproject.literaturaproyecto.modelo.Autor;
import com.desafio.oswaproject.literaturaproyecto.modelo.Libro;
import com.desafio.oswaproject.literaturaproyecto.modelo.LibroDTO;
import com.desafio.oswaproject.literaturaproyecto.modelo.RespuestaDTO;
import com.desafio.oswaproject.literaturaproyecto.repository.AutorRepository;
import com.desafio.oswaproject.literaturaproyecto.repository.LibroRepository;
import com.desafio.oswaproject.literaturaproyecto.service.ConsumoAPI;
import com.desafio.oswaproject.literaturaproyecto.service.ConvierteDatos;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();
    private LibroRepository libroRepository;
    private AutorRepository autorRepository;

    private String libroABuscar;
    private String url = "https://gutendex.com/books/?search=";

    public Principal(LibroRepository libroRepository, AutorRepository autorRepository) {
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
    }

    public void mostrarDatos(){

        var opcion = -1;
        //Mientras no sea 0 se repetira
        while (opcion != 0){
            System.out.println("----------Menu----------");
            var menu = """
                    1) Buscar Libro Por Titulo
                    2) Listar Libros Registrados
                    3) Listar Autores Registrados
                    4) Listar Autores Vivos En Derminado Year
                    5) Listar Libros Por Idioma
                    6) Salir
                    """;
            System.out.println(menu);
            System.out.print("\nSeleccion una opcion: ");
            opcion = teclado.nextInt();
            // Por que a veces no toma bien el enter
            teclado.nextLine();
            switch (opcion){
                case 1:
                    buscarLibroPorTitulo();
                    break;
                case 2:
                    listarLibrosRegistrados();
                    break;
                case 3:
                    listarAutoresRegistrados();
                    break;
                case 4:
                    listarAutoresEntreFechas();
                    break;
                case 5:
                    listarLibrosPorIdioma();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }

        }// Fin While
    } // Fin MostrarDatos



    private RespuestaDTO getRespuesta(){
        System.out.println("Ingrese un libro a buscar: ");
        libroABuscar = teclado.nextLine();

        var json = consumoAPI.obtenerDatos(url+libroABuscar.replace( " ", "+"));
        System.out.println(json);

        RespuestaDTO respuesta = conversor.obtenerDatos(json, RespuestaDTO.class);
        return respuesta;
    }

    private Libro identificarLibro(RespuestaDTO respuestaDTO){
        return respuestaDTO.resultado().stream()
                .filter(l -> l.titulo().equalsIgnoreCase(libroABuscar))
                .findFirst()
                .map(Libro::new) // aquí usas el constructor que recibe LibroDTO
                .orElse(null);
    }

    private void buscarLibroPorTitulo() {
        RespuestaDTO respuesta = getRespuesta();
        Libro libro = identificarLibro(respuesta);

        if (libro != null) {
            libroRepository.save(libro);
            System.out.println("Libro guardado: " + libro.getTitulo());
        } else {
            System.out.println("No se encontró el libro exacto con ese título.");
        }
    }

    private void listarLibrosRegistrados() {
        List<Libro> libroscargados = libroRepository.librosRegistrados();
        libroscargados.forEach(l -> {
            String autores = l.getAutores().stream()
                    .map(Autor::getNombre)
                    .collect(Collectors.joining(", "));

            System.out.println("-----LIBRO-----\n" +
                    "Titulo: " + l.getTitulo() + "\n" +
                    "Autor: " + autores + "\n" +
                    "Idioma: " + l.getIdomas() + "\n" +
                    "Numero de descargas: " + l.getNoDescargas() + "\n");});

    }

    private void listarAutoresRegistrados() {
        List<Autor> autoresRegistrados = autorRepository.autoresRegistrados();
        autoresRegistrados.forEach(a ->
                System.out.println("-----Autores-----\n" +
                        "Nombre: "+a.getNombre()+"\n" +
                        "Año de Nacimiento: "+a.getFechaNacimiento()+"\n" +
                        "Año de Muerte: "+a.getFechaMuerte()+"\n"));
    }

    private void listarAutoresEntreFechas() {
        System.out.print("Ingrese el año para buscar autores: ");
        var fecha = teclado.nextInt();

        List<Autor> autoresEntreFechas = autorRepository.listaDeAutoresEntreAno(fecha);
        autoresEntreFechas.forEach(a ->
                System.out.println("-----Resultados-----\n" +
                        "Nombre: "+a.getNombre()+"\n" +
                        "Año de Nacimiento: "+a.getFechaNacimiento()+"\n" +
                        "Año de Muerte: "+a.getFechaMuerte()+"\n"));
    }

    private void listarLibrosPorIdioma() {
        List<Libro> librosPorIdioma;
        var opcionesIdioma = """
                ---Idiomas---
                1) en -> English
                2) es -> Español
                3) fr -> Frances
                4) de -> Deutsch
                5) it -> Italiano
                """;
        System.out.println(opcionesIdioma);
        System.out.print("Seleccione una opción: ");
        var eleccionIdioma = teclado.nextInt();
        switch (eleccionIdioma){
            case 1:
                mostrarResultadoLibrosIdiomas("en");
                break;
            case 2:
                mostrarResultadoLibrosIdiomas("es");
                break;
            case 3:
                mostrarResultadoLibrosIdiomas("fr");
                break;
            case 4:
                mostrarResultadoLibrosIdiomas("de");
                break;
            case 5:
                mostrarResultadoLibrosIdiomas("it");
                break;
            default:
                System.out.println("Opción inválida");

        }
    }
    private void mostrarResultadoLibrosIdiomas(String codigoIdioma){
        List<Libro> librosPorIdioma = libroRepository.librosPorIdioma(codigoIdioma);

        librosPorIdioma.forEach(l -> System.out.println("Titulo: " + l.getTitulo() +
                ", Idiomas: " + l.getIdomas()));
    }

}
