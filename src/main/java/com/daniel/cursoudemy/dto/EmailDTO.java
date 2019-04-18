package com.daniel.cursoudemy.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

public class EmailDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String email;

    @NotEmpty(message = "Preenchimento Obrigatório")
    @Email(message = "Email Inválido")
    public EmailDTO(){

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
