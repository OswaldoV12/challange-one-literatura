package com.desafio.oswaproject.literaturaproyecto;

import com.desafio.oswaproject.literaturaproyecto.principal.Principal;
import com.desafio.oswaproject.literaturaproyecto.repository.AutorRepository;
import com.desafio.oswaproject.literaturaproyecto.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiteraturaproyectoApplication implements CommandLineRunner {

	@Autowired
	private LibroRepository libroRepository;

	@Autowired
	private AutorRepository autorRepository;

	public static void main(String[] args) {
		SpringApplication.run(LiteraturaproyectoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(libroRepository, autorRepository);
		principal.mostrarDatos();
	}
}
