package com.daniel.cursoudemy.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;

import javax.mail.internet.MimeMessage;

public class MockEmailService extends AbstractEmailService {

    public static final Logger LOG = LoggerFactory.getLogger(MockEmailService.class);

    @Override
    public void sendEmail(SimpleMailMessage msg) {
        LOG.info("Simulando envio de email...");
        LOG.info(msg.toString());
        LOG.info("Email Enviado");
    }

    @Override
    public void sendHtmlEmail(MimeMessage msg) {
        LOG.info("Simulando envio de email em HTML...");
        LOG.info(msg.toString());
        LOG.info("Email Enviado");
    }
}
