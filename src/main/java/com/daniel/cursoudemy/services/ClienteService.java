package com.daniel.cursoudemy.services;

import com.daniel.cursoudemy.domain.Cidade;
import com.daniel.cursoudemy.domain.Cliente;
import com.daniel.cursoudemy.domain.Endereco;
import com.daniel.cursoudemy.domain.enums.Perfil;
import com.daniel.cursoudemy.domain.enums.TipoCliente;
import com.daniel.cursoudemy.dto.ClienteDTO;
import com.daniel.cursoudemy.dto.ClienteNewDTO;
import com.daniel.cursoudemy.exceptions.AuthorizationException;
import com.daniel.cursoudemy.exceptions.DataIntegrityException;
import com.daniel.cursoudemy.exceptions.ObjectNotFoundException;
import com.daniel.cursoudemy.repositories.ClienteRepository;
import com.daniel.cursoudemy.repositories.EnderecoRepository;
import com.daniel.cursoudemy.security.UserSS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.naming.AuthenticationException;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private BCryptPasswordEncoder pe;

    @Autowired
    private ClienteRepository repo;

//    @Autowired
//    private CidadeRepository cidRepo;

    @Autowired
    private EnderecoRepository endRepo;

    @Transactional
    public Cliente insert(Cliente obj) {
        obj.setId(null);
        obj = repo.save(obj);
        endRepo.saveAll(obj.getEnderecos());
        return obj;
    }

    public Cliente find(Integer id) {
        UserSS user= UserService.authenticated();
        if(user==null||!user.hasRole(Perfil.ADMIN)&&!id.equals(user.getId())){
            throw new AuthorizationException("Acesso Negado");
        }

        Optional<Cliente> cliente = repo.findById(id);
        return cliente.orElseThrow(() -> new ObjectNotFoundException("Objeto não Encontrado! ID: " + id
                + ", Tipo: " + Cliente.class.getName()));
    }

    public Cliente update(Cliente obj) {
        Cliente newObj = find(obj.getId());
        updateData(newObj, obj);
        return repo.save(newObj);
    }

    public void delete(Integer id) {
        find(id);
        try {
            repo.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Não é possivel excluir o cliente porque possui entidades relacionadas");
        }
    }

    public List<Cliente> findAll() {
        return repo.findAll();
    }

    public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return repo.findAll(pageRequest);
    }

    // Converter cliente DTO para cliente
    public Cliente fromDTO(ClienteDTO objDto) {
        Cliente cli = new Cliente(objDto.getId(), objDto.getNome(), objDto.getEmail(), null, null,null);
        return cli;
    }

    public Cliente fromDTO(ClienteNewDTO objDto) {
        Cliente cli = new Cliente(null, objDto.getNome(), objDto.getEmail(), objDto.getCpfOuCnpj(), TipoCliente.toEnum(objDto.getTipo()),pe.encode(objDto.getSenha()));

        Cidade cid= new Cidade(objDto.getCidadeId(),null,null);


        Endereco end = new Endereco(null, objDto.getLogradouro(), objDto.getNumero(), objDto.getComplemento(), objDto.getBairro(),
                objDto.getCep(), cli, cid);

        cli.getEnderecos().add(end);
        cli.getTelefones().add(objDto.getTelefone1());

        if(objDto.getTelefone2()!=null){
            cli.getTelefones().add(objDto.getTelefone2());
        }

        if(objDto.getTelefone3()!=null){
            cli.getTelefones().add(objDto.getTelefone3());
        }
        return cli;
    }

    //Recebe o Cliente antigo e atualiza  de acordo com o novo objeto
    private void updateData(Cliente newObj, Cliente obj) {
        newObj.setNome(obj.getNome());
        newObj.setEmail(obj.getEmail());
    }

}
