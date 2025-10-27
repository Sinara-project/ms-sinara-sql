package org.example.sinara.controller;

import jakarta.validation.groups.Default;
import org.example.sinara.dto.request.SenhaRequestDTO;
import org.example.sinara.dto.request.EmpresaRequestDTO;
import org.example.sinara.dto.response.EmpresaResponseDTO;
import org.example.sinara.open_api.EmpresaOpenApi;
import org.example.sinara.service.EmpresaService;
import org.example.sinara.validation.OnCreate;
import org.example.sinara.validation.OnPatch;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/empresa")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class EmpresaController implements EmpresaOpenApi {

    private final EmpresaService empresaService;

    public EmpresaController(EmpresaService empresaService) {
        this.empresaService = empresaService;
    }

    //    Métodos comuns
    @GetMapping("buscarPorId/{id}")
    public ResponseEntity<EmpresaResponseDTO> buscarEmpresaPorId(@PathVariable Integer id) {
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
    public ResponseEntity<String> excluirEmpresa(@PathVariable Integer id) {
        empresaService.excluirEmpresa(id);
        return ResponseEntity.ok("Empresa excluída com sucesso!");
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<String> atualizarEmpresa(@PathVariable Integer id,
                                                   @Validated({OnPatch.class, Default.class}) @RequestBody EmpresaRequestDTO dto) {
        empresaService.atualizarEmpresa(id, dto);
        return ResponseEntity.ok("Empresa atualizado com sucesso!");
    }

    @PatchMapping("/atualizarSenhaAreaRestrita/{id}")
    public ResponseEntity<EmpresaResponseDTO> atualizarSenhaAreaRestrita(@PathVariable Integer id,
                                                                         @RequestBody SenhaRequestDTO request) {
        EmpresaResponseDTO atualizado = empresaService.atualizarSenhaAreaRestrita(id, request.getNovaSenha());
        return ResponseEntity.ok(atualizado);
    }

//    Query
    @GetMapping("/listarPerfilEmpresa/{id}")
    public ResponseEntity<Map<String, Object>> buscarPerfil(@PathVariable Integer id) {
        Map<String, Object> perfil = empresaService.buscarPerfilEmpresaPorId(id);
        return ResponseEntity.ok(perfil);
    }

//    Métodos derevidos
    @GetMapping("/obterId/{cnpj}")
    public String obterId(@PathVariable String cnpj) {
        return empresaService.obterIdEmpresaPorCnpj(cnpj);
    }

//    function
    @PostMapping("/rebaixarPlanos")
    public ResponseEntity<String> rebaixarPlanos() {
        empresaService.rebaixarPlanos();
        return ResponseEntity.ok("Planos rebaixados com sucesso!");
    }

//    Procedure
    @PostMapping("/mudar-plano")
    public String mudarParaPremium(@RequestParam Integer idEmpresa, @RequestParam Integer idCartao) {
        return empresaService.mudarParaPremium(idEmpresa, idCartao);
    }

}
