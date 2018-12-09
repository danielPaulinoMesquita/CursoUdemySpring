package com.daniel.cursoudemy;

import com.daniel.cursoudemy.Repositories.CategoriaRepository;
import com.daniel.cursoudemy.domain.Categoria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.Arrays;


@SpringBootApplication
public class CursoudemyApplication implements CommandLineRunner {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public static void main(String[] args) {
        SpringApplication.run(CursoudemyApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Categoria cat1 = new Categoria(null, "informática");
        Categoria cat2 = new Categoria(null, "Escritório");

        // Necessário para rodar o BD H2 que fica na memória.
        categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
    }
}
