package org.example.sinara.service;

import com.sendgrid.*;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Content;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class EmailService {

    @Value("${sendgrid.api.key}")
    private String sendGridApiKey;

    public void enviarEmail(String destinatario, String assunto, String conteudo) throws IOException {
        Email from = new Email("sinaraoficial.suporte@gmail.com");
        Email to = new Email(destinatario);
        Content content = new Content("text/plain", conteudo);
        Mail mail = new Mail(from, assunto, to, content);

        SendGrid sg = new SendGrid(sendGridApiKey);
        Request request = new Request();

        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            System.out.println("Enviando e-mail para: " + destinatario);
            System.out.println("API Key iniciando com: " + sendGridApiKey.substring(0, 5));
            sg.api(request);
            Response response = sg.api(request);
            System.out.println("Status: " + response.getStatusCode());
            System.out.println("Body: " + response.getBody());
            System.out.println("Headers: " + response.getHeaders());
        } catch (IOException ex) {
            throw ex;
        }
    }
}