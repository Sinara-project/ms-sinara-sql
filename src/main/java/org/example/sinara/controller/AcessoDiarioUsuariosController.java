package org.example.sinara.controller;

import org.example.sinara.model.AcessoDiarioUsuarios;
import org.example.sinara.open_api.AcessoDiarioUsuariosOpenApi;
import org.example.sinara.service.AcessoDiarioUsuariosService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/acessoDiarioUsuarios")
public class AcessoDiarioUsuariosController implements AcessoDiarioUsuariosOpenApi {

    private final AcessoDiarioUsuariosService service;

    public AcessoDiarioUsuariosController(AcessoDiarioUsuariosService service) {
        this.service = service;
    }

    @GetMapping("/listar")
    public ResponseEntity<List<AcessoDiarioUsuarios>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @PostMapping("/registrar")
    public String registrar(@RequestBody AcessoDiarioUsuarios request) {
        service.registrar(request);
        return "Atividade registrada com sucesso!";
    }
}
