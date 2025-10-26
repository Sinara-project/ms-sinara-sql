package org.example.sinara.controller;

import jakarta.validation.groups.Default;
import org.example.sinara.dto.request.OperarioRequestDTO;
import org.example.sinara.dto.response.OperarioResponseDTO;
import org.example.sinara.model.Operario;
import org.example.sinara.open_api.OperarioOpenApi;
import org.example.sinara.service.OperarioService;
import org.example.sinara.validation.OnCreate;
import org.example.sinara.validation.OnPatch;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user/operario")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class OperarioController implements OperarioOpenApi {
    private final OperarioService operarioService;

    public OperarioController(OperarioService operarioService) {
        this.operarioService = operarioService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<OperarioResponseDTO> buscarOperarioPorId(@PathVariable Integer id) {
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
    public ResponseEntity<String> excluirOperario(@PathVariable Integer id) {
        operarioService.excluirOperario(id);
        return ResponseEntity.ok("Operário excluído com sucesso!");
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<String> atualizarOperario(@PathVariable Integer id,
                                                  @Validated({OnPatch.class, Default.class}) @RequestBody OperarioRequestDTO dto) {
        operarioService.atualizarOperario(id, dto);
        return ResponseEntity.ok("Operário atualizado com sucesso!");
    }

    //    Query
    @GetMapping("/perfil-operario/{id}")
    public ResponseEntity<Map<String, Object>> buscarPerfil(@PathVariable Integer id) {
        Map<String, Object> perfil = operarioService.buscarPerfilOperarioPorId(id);
        return ResponseEntity.ok(perfil);
    }

    //    Reconhecimento facial
    @PostMapping("/uploadReconhecimento/{id}")
    public ResponseEntity<String> uploadFotoReconhecimento(
            @PathVariable Integer id,
            @RequestParam("file") MultipartFile file) {
        operarioService.atualizarFotoReconhecimento(id, file);
        return ResponseEntity.ok("Imagem de reconhecimento facial salva com sucesso!");
    }

    @GetMapping("/verificarReconhecimento/{id}")
    public ResponseEntity<Boolean> verificarReconhecimento(@PathVariable Integer id) {
        boolean resultado = operarioService.verificarRosto(id);
        return ResponseEntity.ok(resultado);
    }

}
