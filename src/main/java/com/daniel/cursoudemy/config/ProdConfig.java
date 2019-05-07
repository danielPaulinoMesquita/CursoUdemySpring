package com.daniel.cursoudemy.config;

import com.daniel.cursoudemy.services.DBService;
import com.daniel.cursoudemy.services.EmailService;
import com.daniel.cursoudemy.services.SmtpEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.text.ParseException;

/**
 * Prod config, test config são responsáveis por escolher o aplication.properties e
 * assim escolher o banco de teste ou do MySql
 */
@Configuration
@Profile("prod")
public class ProdConfig {

    @Autowired
    private DBService dbService;

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String strategy;

    @Bean
    public boolean instantiateDatabase() throws ParseException {

        if("create".equals(strategy)) {
            dbService.instantiateTestDatabase();
            return true;
        }
        return false;
    }

    @Bean
    public EmailService emailService(){
        return new SmtpEmailService();
    }//<-- Antivirus do seu pc, pode bloquear o envio
}
