package org.example.sinara.controller;

import jakarta.validation.groups.Default;
import org.example.sinara.dto.request.PermissoesRequestDTO;
import org.example.sinara.dto.response.PermissoesResponseDTO;
import org.example.sinara.service.PermissoesService;
import org.example.sinara.validation.OnCreate;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/permissoes")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class PermissoesController {
    private final PermissoesService permissoesService;

    public PermissoesController(PermissoesService permissoesService) {
        this.permissoesService = permissoesService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<PermissoesResponseDTO> buscarPermissoesPorId(@PathVariable Long id) {
        PermissoesResponseDTO permissoes = permissoesService.buscarPorId(id);
        return ResponseEntity.ok(permissoes);
    }

    @GetMapping("/listar")
    public List<PermissoesResponseDTO> listarPermissoes(){
        return permissoesService.listarPermissoes();
    }

    @PostMapping("/inserir")
    public ResponseEntity<String> inserirPermissoes(@Validated({OnCreate.class, Default.class})
                                                        @RequestBody PermissoesRequestDTO dto) {
        permissoesService.inserirPermissoes(dto);
        return ResponseEntity.ok("Permissoes inseridas com sucesso!");
    }

    @DeleteMapping("/excluir/{id}")
    public ResponseEntity<String> excluirPermissoes(@PathVariable Long id) {
        permissoesService.excluirPermissoes(id);
        return ResponseEntity.ok("Permissoes excluída com sucesso!");
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<String> atualizarPermissoes(@PathVariable Long id,
                                                    @Validated({OnCreate.class, Default.class})
                                                    @RequestBody PermissoesRequestDTO dto) {
        permissoesService.atualizarPermissoes(id, dto);
        return ResponseEntity.ok("Permissoes atualizadas com sucesso!");
    }
}
