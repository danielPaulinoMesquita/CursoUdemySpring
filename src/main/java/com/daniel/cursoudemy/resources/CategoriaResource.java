package com.daniel.cursoudemy.resources;

import com.daniel.cursoudemy.Services.CategoriaService;
import com.daniel.cursoudemy.domain.Categoria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="/categorias")
public class CategoriaResource {

    @Autowired
    private CategoriaService service;

    @RequestMapping(value="/{id}", method= RequestMethod.GET)
    public ResponseEntity<Categoria> find(@PathVariable Integer id){
            Categoria obj= service.buscar(id);

      //  return new ResponseEntity<>(obj, HttpStatus.OK); OUTRA FORMA DE RETORNA O OBJ EM JSON
        return ResponseEntity.ok().body(obj);
    }

}
