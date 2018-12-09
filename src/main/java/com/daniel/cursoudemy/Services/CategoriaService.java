package com.daniel.cursoudemy.Services;

import com.daniel.cursoudemy.Repositories.CategoriaRepository;
import com.daniel.cursoudemy.domain.Categoria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository repo;

    public Categoria buscar(Integer id){
        Optional<Categoria> categoria=repo.findById(id);
        return categoria.orElse(null);
    }

}
