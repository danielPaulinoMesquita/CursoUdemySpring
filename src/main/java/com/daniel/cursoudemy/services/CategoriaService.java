package com.daniel.cursoudemy.services;

import com.daniel.cursoudemy.exceptions.ObjectNotFoundException;
import com.daniel.cursoudemy.repositories.CategoriaRepository;
import com.daniel.cursoudemy.domain.Categoria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository repo;

    public Categoria buscar(Integer id) {
        Optional<Categoria> categoria = repo.findById(id);
        return categoria.orElseThrow(() -> new ObjectNotFoundException("Objeto não Encontrado! ID: " + id
                + ", Tipo: " + Categoria.class.getName()));
    }

}
