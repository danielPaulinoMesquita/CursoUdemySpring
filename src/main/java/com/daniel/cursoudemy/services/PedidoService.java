package com.daniel.cursoudemy.services;

import com.daniel.cursoudemy.domain.*;
import com.daniel.cursoudemy.domain.enums.EstadoPagamento;
import com.daniel.cursoudemy.exceptions.AuthorizationException;
import com.daniel.cursoudemy.exceptions.ObjectNotFoundException;
import com.daniel.cursoudemy.repositories.ItemPedidoRepository;
import com.daniel.cursoudemy.repositories.PagamentoRepository;
import com.daniel.cursoudemy.repositories.PedidoRepository;
import com.daniel.cursoudemy.security.UserSS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Service
public class PedidoService {

    @Autowired
    private BoletoService boletoService;

    @Autowired
    private PedidoRepository repo;

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private EmailService emailService;

    public Pedido find(Integer id) {
        Optional<Pedido> pedido = repo.findById(id);
        return pedido.orElseThrow(() -> new ObjectNotFoundException("Objeto não Encontrado! ID: " + id
                + ", Tipo: " + Pedido.class.getName()));
    }

    @Transactional
    public Pedido insert(Pedido obj) {
        obj.setId(null);
        obj.setInstante(new Date());
        obj.setCliente(clienteService.find(obj.getCliente().getId()));
        obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
        obj.getPagamento().setPedido(obj);

        if (obj.getPagamento() instanceof PagamentoComBoleto) {
            PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
            boletoService.preencherPagamentoComBoleto(pagto, obj.getInstante());
        }

        obj = repo.save(obj);
        pagamentoRepository.save(obj.getPagamento());

        for(ItemPedido ip: obj.getItens()){
            ip.setDesconto(0.0);
            ip.setProduto(produtoService.find(ip.getProduto().getId()));
            ip.setPreco(ip.getProduto().getPreco());
            ip.setPedido(obj);
        }
        itemPedidoRepository.saveAll(obj.getItens());
        emailService.sendOrderConfirmationHtmlEmail(obj);
        return obj;
    }
//Buscar Pedidos por cliente
    public Page<Pedido> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
        UserSS user= UserService.authenticated();
        if(user==null){
            throw new AuthorizationException("Acesso Negado");
        }
        Cliente cliente= clienteService.find(user.getId());
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction),orderBy);
        return repo.findByCliente(cliente,pageRequest);
    }


}
