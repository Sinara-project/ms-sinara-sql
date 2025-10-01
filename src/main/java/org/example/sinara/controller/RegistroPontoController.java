package org.example.sinara.controller;

import jakarta.validation.groups.Default;
import org.example.sinara.dto.request.RegistroPontoRequestDTO;
import org.example.sinara.dto.response.RegistroPontoResponseDTO;
import org.example.sinara.service.RegistroPontoService;
import org.example.sinara.validation.OnCreate;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/registroPonto")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class RegistroPontoController {
    private final RegistroPontoService registroPontoService;

    public RegistroPontoController(RegistroPontoService registroPontoService) {
        this.registroPontoService = registroPontoService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<RegistroPontoResponseDTO> buscarRegistroPontoPorId(@PathVariable Long id) {
        RegistroPontoResponseDTO registroPonto = registroPontoService.buscarPorId(id);
        return ResponseEntity.ok(registroPonto);
    }

    @GetMapping("/listar")
    public List<RegistroPontoResponseDTO> listarRegistroPonto(){
        return registroPontoService.listarRegistroPonto();
    }

    @PostMapping("/inserir")
    public ResponseEntity<String> inserirRegistroPonto(@Validated({OnCreate.class, Default.class})
                                                    @RequestBody RegistroPontoRequestDTO dto) {
        registroPontoService.inserirRegistroPonto(dto);
        return ResponseEntity.ok("Registro de ponto inserido com sucesso!");
    }

    @DeleteMapping("/excluir/{id}")
    public ResponseEntity<String> excluirRegistroPonto(@PathVariable Long id) {
        registroPontoService.excluirRegistroPonto(id);
        return ResponseEntity.ok("Registro de ponto excluído com sucesso!");
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<String> atualizarRegistroPonto(@PathVariable Long id,
                                                      @Validated({OnCreate.class, Default.class})
                                                      @RequestBody RegistroPontoRequestDTO dto) {
        registroPontoService.atualizarRegistroPonto(id, dto);
        return ResponseEntity.ok("Registro de ponto atualizado com sucesso!");
    }
}
