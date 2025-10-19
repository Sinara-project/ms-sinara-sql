package org.example.sinara.controller;

import jakarta.validation.groups.Default;
import org.example.sinara.dto.request.EmpresaRequestDTO;
import org.example.sinara.dto.response.EmpresaResponseDTO;
import org.example.sinara.model.Empresa;
import org.example.sinara.open_api.EmpresaOpenApi;
import org.example.sinara.service.EmpresaService;
import org.example.sinara.validation.OnCreate;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/empresa")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class EmpresaController implements EmpresaOpenApi {

    private final EmpresaService empresaService;

    public EmpresaController(EmpresaService empresaService) {
        this.empresaService = empresaService;
    }

    //    Métodos comuns

    @GetMapping("/{id}")
    public ResponseEntity<EmpresaResponseDTO> buscarEmpresaPorId(@PathVariable Long id) {
        EmpresaResponseDTO empresa = empresaService.buscarPorId(id);
        return ResponseEntity.ok(empresa);
    }

    @GetMapping("/listar")
    public List<EmpresaResponseDTO> listarEmpresas(){
        return empresaService.listarEmpresas();
    }

    @PostMapping("/inserir")
    public ResponseEntity<String> inserirEmpresa(@Validated({OnCreate.class, Default.class}) @RequestBody EmpresaRequestDTO dto) {
        empresaService.inserirEmpresa(dto);
        return ResponseEntity.ok("Empresa inserida com sucesso!");
    }

    @DeleteMapping("/excluir/{id}")
    public ResponseEntity<String> excluirEmpresa(@PathVariable Long id) {
        empresaService.excluirEmpresa(id);
        return ResponseEntity.ok("Empresa excluída com sucesso!");
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<String> atualizarEmpresa(@PathVariable Long id,
                                                   @Validated({OnCreate.class, Default.class}) @RequestBody EmpresaRequestDTO dto) {
        empresaService.atualizarEmpresa(id, dto);
        return ResponseEntity.ok("Empresa atualizado com sucesso!");
    }

//    Query
    @GetMapping("/perfilEmpresa/{id}")
    public ResponseEntity<Map<String, Object>> buscarPerfil(@PathVariable Long id) {
        Map<String, Object> perfil = empresaService.buscarPerfilEmpresaPorId(id);
        return ResponseEntity.ok(perfil);
    }
}
