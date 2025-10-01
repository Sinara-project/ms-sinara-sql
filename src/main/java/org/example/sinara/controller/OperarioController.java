package org.example.sinara.controller;

import jakarta.validation.groups.Default;
import org.example.sinara.dto.request.OperarioRequestDTO;
import org.example.sinara.dto.response.OperarioResponseDTO;
import org.example.sinara.service.OperarioService;
import org.example.sinara.validation.OnCreate;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/operario")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class OperarioController {
    private final OperarioService operarioService;

    public OperarioController(OperarioService operarioService) {
        this.operarioService = operarioService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<OperarioResponseDTO> buscarOperarioPorId(@PathVariable Long id) {
        OperarioResponseDTO operario = operarioService.buscarPorId(id);
        return ResponseEntity.ok(operario);
    }

    @GetMapping("/listar")
    public List<OperarioResponseDTO> listarOperario(){
        return operarioService.listarOperario();
    }

    @PostMapping("/inserir")
    public ResponseEntity<String> inserirOperario(@Validated({OnCreate.class, Default.class}) @RequestBody OperarioRequestDTO dto) {
        operarioService.inserirOperario(dto);
        return ResponseEntity.ok("Operário inserido com sucesso!");
    }

    @DeleteMapping("/excluir/{id}")
    public ResponseEntity<String> excluirOperario(@PathVariable Long id) {
        operarioService.excluirOperario(id);
        return ResponseEntity.ok("Operário excluída com sucesso!");
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<String> atualizarOperario(@PathVariable Long id,
                                                  @Validated({OnCreate.class, Default.class}) @RequestBody OperarioRequestDTO dto) {
        operarioService.atualizarOperario(id, dto);
        return ResponseEntity.ok("Operário atualizado com sucesso!");
    }
}
