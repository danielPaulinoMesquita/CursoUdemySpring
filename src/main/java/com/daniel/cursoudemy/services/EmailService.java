package com.daniel.cursoudemy.services;

import com.daniel.cursoudemy.domain.Pedido;
import org.springframework.mail.SimpleMailMessage;
import javax.mail.internet.MimeMessage;

public interface EmailService {

    void sendOrderConfimationEmail(Pedido obj);

    void sendEmail(SimpleMailMessage msg);

    void sendOrderConfirmationHtmlEmail(Pedido obj);

    void sendHtmlEmail(MimeMessage msg);
}
