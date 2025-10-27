package org.example.sinara.controller;

import org.example.sinara.model.AcessoDiarioUsuarios;
import org.example.sinara.service.AcessoDiarioUsuariosService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/acessoDiarioUsuarios")
public class AcessoDiarioUsuariosController {

    private final AcessoDiarioUsuariosService service;

    public AcessoDiarioUsuariosController(AcessoDiarioUsuariosService service) {
        this.service = service;
    }

    @PostMapping("/registrar")
    public String registrar(@RequestBody AcessoDiarioUsuarios request) {
        service.registrar(request);
        return "Atividade registrada com sucesso!";
    }
}
