package com.daniel.cursoudemy.services;

import com.amazonaws.services.licensemanager.model.LicenseSpecification;
import com.daniel.cursoudemy.domain.Cidade;
import com.daniel.cursoudemy.repositories.CidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CidadeService {

    @Autowired
    private CidadeRepository cidadeRepository;

    public List<Cidade> findByEstado(Integer estado_id){
        return cidadeRepository.findCidades(estado_id);
    }

}
