package org.example.sinara.controller;

import org.example.sinara.model.Operario;
import org.example.sinara.repository.sql.OperarioRepository;
import org.example.sinara.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/esqueciMinhaSenha")
public class EsqueciSenhaController {

    @Autowired
    private OperarioRepository operarioRepository;

    @Autowired
    private EmailService emailService;

    @PostMapping
    public ResponseEntity<?> enviarEmailRedefinicao(@RequestBody String email) {
        email = email.replace("\"", "").trim();

        Optional<Operario> operarioOpt = operarioRepository.findByEmailIgnoreCase(email);
        if (operarioOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("E-mail não encontrado");
        }

        String token = UUID.randomUUID().toString();

        String link = "https://ms-sinara-sql-oox0.onrender.com/empresa/redefinir?token=" + token;

        try {
            emailService.enviarEmail(
                    email,
                    "Redefinição de senha - Sinara",
                    "Olá!\n\nClique no link abaixo para redefinir sua senha:\n" + link +
                            "\n\nSe você não solicitou essa redefinição, ignore este e-mail."
            );
            return ResponseEntity.ok("E-mail de redefinição enviado com sucesso!");
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Erro ao enviar e-mail: " + e.getMessage());
        }
    }
}
