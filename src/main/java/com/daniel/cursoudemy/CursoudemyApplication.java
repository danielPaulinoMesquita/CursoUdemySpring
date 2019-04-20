package com.daniel.cursoudemy;


import com.daniel.cursoudemy.services.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CursoudemyApplication implements CommandLineRunner {

    @Autowired
    private S3Service s3Service;

    public static void main(String[] args) {
        SpringApplication.run(CursoudemyApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        s3Service.uploadFile("//home//daniel//Imagens//finalfantasyx.jpeg");

    }
}
