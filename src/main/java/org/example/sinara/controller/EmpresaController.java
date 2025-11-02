package org.example.sinara.controller;

import jakarta.validation.groups.Default;
import org.example.sinara.dto.request.EmpresaLoginRequestDTO;
import org.example.sinara.dto.request.EmpresaRequestDTO;
import org.example.sinara.dto.request.LoginRestritoRequestDTO;
import org.example.sinara.dto.request.SenhaRequestDTO;
import org.example.sinara.dto.response.EmpresaLoginResponseDTO;
import org.example.sinara.dto.response.EmpresaResponseDTO;
import org.example.sinara.open_api.EmpresaOpenApi;
import org.example.sinara.service.EmpresaService;
import org.example.sinara.validation.OnCreate;
import org.example.sinara.validation.OnPatch;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/empresa")
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
    public ResponseEntity<EmpresaLoginResponseDTO> inserirEmpresa(
            @Validated({OnCreate.class, Default.class})
            @RequestBody EmpresaRequestDTO dto) {

        EmpresaLoginResponseDTO novaEmpresa = empresaService.inserirEmpresa(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaEmpresa);
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

    @PatchMapping("/atualizarSenha/{id}")
    public ResponseEntity<EmpresaResponseDTO> atualizarSenha(@PathVariable Integer id,
                                                                         @RequestBody SenhaRequestDTO request) {
        EmpresaResponseDTO atualizado = empresaService.atualizarSenha(id, request.getNovaSenha());
        return ResponseEntity.ok(atualizado);
    }

    @PostMapping("/loginAreaRestrita")
    public ResponseEntity<Boolean> loginAreaRestrita(@RequestBody LoginRestritoRequestDTO dto) {
        boolean autenticado = empresaService.loginAreaRestrita(dto.getIdEmpresa(), dto.getSenha());
        return ResponseEntity.ok(autenticado);
    }

    @PostMapping("/validarCodigo")
    public ResponseEntity<Boolean> validarCodigo(@RequestParam String cnpj,
                                                 @RequestParam String codigo) {
        boolean valido = empresaService.validarCodigoPorCnpj(cnpj, codigo);
        return ResponseEntity.ok(valido);
    }

    //    Query
    @GetMapping("/listarPerfilEmpresa/{id}")
    public ResponseEntity<Map<String, Object>> buscarPerfil(@PathVariable Integer id) {
        Map<String, Object> perfil = empresaService.buscarPerfilEmpresaPorId(id);
        return ResponseEntity.ok(perfil);
    }

    @GetMapping("/obterEmpresaPorCnpj/{cnpj}")
    public ResponseEntity<Map<String, Object>> obterIdECodigo(@PathVariable String cnpj) {
        Map<String, Object> dados = empresaService.obterIdEmpresaPorCnpj(cnpj);
        return ResponseEntity.ok(dados);
    }

    @PostMapping("/loginEmpresa")
    public ResponseEntity<?> login(@RequestBody EmpresaLoginRequestDTO loginDTO) {
        try {
            EmpresaLoginResponseDTO empresa = empresaService.loginEmpresa(loginDTO.getCnpj(), loginDTO.getSenha());
            return ResponseEntity.ok(empresa);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    //    function
    @PostMapping("/rebaixarPlanos")
    public ResponseEntity<String> rebaixarPlanos() {
        empresaService.rebaixarPlanos();
        return ResponseEntity.ok("Planos rebaixados com sucesso!");
    }

//    Procedure
    @PostMapping("/mudarPlanoParaPremium")
    public String mudarParaPremium(@RequestParam Integer idEmpresa, @RequestParam Integer idCartao) {
        return empresaService.mudarParaPremium(idEmpresa, idCartao);
    }

}
