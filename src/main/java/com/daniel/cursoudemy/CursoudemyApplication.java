package com.daniel.cursoudemy;

import com.daniel.cursoudemy.Repositories.CategoriaRepository;
import com.daniel.cursoudemy.Repositories.ProdutoRepository;
import com.daniel.cursoudemy.domain.Categoria;
import com.daniel.cursoudemy.domain.Produto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;


@SpringBootApplication
public class CursoudemyApplication implements CommandLineRunner {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    public static void main(String[] args) {
        SpringApplication.run(CursoudemyApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Categoria cat1 = new Categoria(null, "informática");
        Categoria cat2 = new Categoria(null, "Escritório");

        Produto p1 = new Produto(null, "Computador", 2800.00);
        Produto p2 = new Produto(null, "Iimpressora", 800.00);
        Produto p3 = new Produto(null, "Mouse", 80.00);

        cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
        cat2.getProdutos().add(p2);

        p1.getCategorias().add(cat1);
        p2.getCategorias().addAll(Arrays.asList(cat1,cat2));
        p3.getCategorias().add(cat1);

        // Necessário para rodar o BD H2 que fica na memória.
        categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
        produtoRepository.saveAll(Arrays.asList(p1,p2,p3));
    }
}
