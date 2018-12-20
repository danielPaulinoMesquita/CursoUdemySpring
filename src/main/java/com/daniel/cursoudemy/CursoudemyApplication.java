package com.daniel.cursoudemy;

import com.daniel.cursoudemy.domain.*;
import com.daniel.cursoudemy.domain.enums.EstadoPagamento;
import com.daniel.cursoudemy.domain.enums.TipoCliente;
import com.daniel.cursoudemy.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.text.SimpleDateFormat;
import java.util.Arrays;


@SpringBootApplication
public class CursoudemyApplication implements CommandLineRunner {


    public static void main(String[] args) {
        SpringApplication.run(CursoudemyApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {



    }
}
