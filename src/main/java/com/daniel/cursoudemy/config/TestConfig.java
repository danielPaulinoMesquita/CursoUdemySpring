package com.daniel.cursoudemy.config;

import com.daniel.cursoudemy.services.DBService;
import com.daniel.cursoudemy.services.EmailService;
import com.daniel.cursoudemy.services.MockEmailService;
import org.hibernate.validator.constraints.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.text.ParseException;

@Configuration
@Profile("test")
public class TestConfig {

    @Autowired
    private DBService dbService;

    @Bean
    public boolean instantiateDatabase() throws ParseException {
        dbService.instantiateTestDatabase(); // MANDANDO O JAVA CRIAR O QUE ESTÁ DENTRO DO DBSERVICE
        return true;
    }

    @Bean
    public EmailService emailService(){
        return new MockEmailService();
    }
}
