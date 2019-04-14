package com.daniel.cursoudemy.resources;

import com.daniel.cursoudemy.domain.Produto;
import com.daniel.cursoudemy.dto.ProdutoDTO;
import com.daniel.cursoudemy.resources.utils.URL;
import com.daniel.cursoudemy.services.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/produtos")
public class ProdutoResource {

    @Autowired
    private ProdutoService service;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Produto> find(@PathVariable Integer id) {

        Produto obj = service.find(id);
        //  return new ResponseEntity<>(obj, HttpStatus.OK); OUTRA FORMA DE RETORNA O OBJ EM JSON
        return ResponseEntity.ok().body(obj);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Page<ProdutoDTO>> findPage(
            @RequestParam(value="nome",defaultValue="") String nome,
            @RequestParam(value="categorias",defaultValue="") String categorias,
            @RequestParam(value="page",defaultValue="0") Integer page,
            @RequestParam(value="linesPerPage",defaultValue="24") Integer linesPerPage,
            @RequestParam(value="orderBy",defaultValue="nome") String orderBy,
            @RequestParam(value="direction",defaultValue="ASC") String direction) {

        String nomeDecode=URL.decodeParam(nome);
        List<Integer> ids=URL.decodeIntList(categorias);
        Page<Produto> list = service.search(nomeDecode, ids, page,
                linesPerPage, orderBy, direction);

        Page<ProdutoDTO> listDto= list.map(obj -> new ProdutoDTO(obj));

        //  return new ResponseEntity<>(obj, HttpStatus.OK); OUTRA FORMA DE RETORNAR O OBJ EM JSON
        return ResponseEntity.ok().body(listDto);
    }

}
