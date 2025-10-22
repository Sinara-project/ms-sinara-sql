package org.example.sinara.controller;

import jakarta.validation.groups.Default;
import org.example.sinara.dto.request.PlanosRequestDTO;
import org.example.sinara.dto.response.PlanosResponseDTO;
import org.example.sinara.open_api.PlanosOpenApi;
import org.example.sinara.service.PlanosService;
import org.example.sinara.validation.OnCreate;
import org.example.sinara.validation.OnPatch;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/planos")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class PlanosController implements PlanosOpenApi {
    private final PlanosService planosService;

    public PlanosController(PlanosService planosService) {
        this.planosService = planosService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlanosResponseDTO> buscarPlanosPorId(@PathVariable Integer id) {
        PlanosResponseDTO planos = planosService.buscarPorId(id);
        return ResponseEntity.ok(planos);
    }

    @GetMapping("/listar")
    public List<PlanosResponseDTO> listarPlanos(){
        return planosService.listarPlanos();
    }

    @PostMapping("/inserir")
    public ResponseEntity<String> inserirPlanos(@Validated({OnCreate.class, Default.class})
                                                   @RequestBody PlanosRequestDTO dto) {
        planosService.inserirPlanos(dto);
        return ResponseEntity.ok("Plano inserido com sucesso!");
    }

    @DeleteMapping("/excluir/{id}")
    public ResponseEntity<String> excluirPlanos(@PathVariable Integer id) {
        planosService.excluirPlanos(id);
        return ResponseEntity.ok("Plano excluído com sucesso!");
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<String> atualizarPlanos(@PathVariable Integer id,
                                                     @Validated({OnPatch.class, Default.class}) @RequestBody PlanosRequestDTO dto) {
        planosService.atualizarPlanos(id, dto);
        return ResponseEntity.ok("Plano atualizado com sucesso!");
    }
}
