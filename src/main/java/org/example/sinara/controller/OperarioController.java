package org.example.sinara.controller;

import jakarta.validation.groups.Default;
import org.example.sinara.dto.request.OperarioLoginRequestDTO;
import org.example.sinara.dto.request.OperarioRequestDTO;
import org.example.sinara.dto.response.OperarioResponseDTO;
import org.example.sinara.model.Operario;
import org.example.sinara.open_api.OperarioOpenApi;
import org.example.sinara.service.OperarioService;
import org.example.sinara.utils.ReconhecimentoFacial;
import org.example.sinara.validation.OnCreate;
import org.example.sinara.validation.OnPatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user/operario")
public class OperarioController implements OperarioOpenApi {
    private final OperarioService operarioService;
    @Autowired
    private ReconhecimentoFacial reconhecimentoFacial;

    public OperarioController(OperarioService operarioService) {
        this.operarioService = operarioService;
    }

//    Métodos comuns
    @GetMapping("buscarPorId/{id}")
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

    @PostMapping("/verificarSenha")
    public ResponseEntity<Boolean> verificarSenha(
            @RequestParam Integer idOperario,
            @RequestParam String senha) {
        boolean valida = operarioService.verificarSenha(idOperario, senha);
        return ResponseEntity.ok(valida);
    }

    //    Query
    @GetMapping("/perfilOperario/{id}")
    public ResponseEntity<Map<String, Object>> buscarPerfil(@PathVariable Integer id) {
        Map<String, Object> perfil = operarioService.buscarPerfilOperarioPorId(id);
        return ResponseEntity.ok(perfil);
    }

    @GetMapping("/obterId/{cpf}")
    public String obterId(@PathVariable String cpf) {
        return operarioService.obterIdOperarioPorCpf(cpf);
    }

    @GetMapping("/listarOperariosPorIdEmpresa/{idEmpresa}")
    public ResponseEntity<List<Map<String, Object>>> listarOperariosPorEmpresa(@PathVariable Integer idEmpresa) {
        List<Map<String, Object>> operarios = operarioService.buscarOperariosPorIdEmpresa(idEmpresa);
        return ResponseEntity.ok(operarios);
    }

    @PostMapping("/loginOperario")
    public ResponseEntity<Boolean> loginOperario(@RequestBody OperarioLoginRequestDTO loginDTO) {
        boolean valido = operarioService.validarLogin(loginDTO);
        return ResponseEntity.ok(valido);
    }

    //    Procedure
    @PostMapping("/atualizarStatus")
    public String atualizarStatus(@RequestParam Integer idOperario,
                                  @RequestParam Boolean ativo,
                                  @RequestParam Boolean ferias) {
        return operarioService.atualizarStatus(idOperario, ativo, ferias);
    }

    //    Reconhecimento facial
    @PostMapping(value = "/uploadReconhecimento/{id}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadFotoReconhecimento(
            @PathVariable Integer id,
            @RequestParam("file") MultipartFile file) {
        operarioService.atualizarFotoReconhecimento(id, file);
        return ResponseEntity.ok("Imagem de reconhecimento facial salva com sucesso!");
    }

    @PostMapping(value = "/verificarFacial",
        consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Boolean> verificarFacial(
            @RequestParam("userId") Integer userId,
            @RequestParam("fotoTeste") MultipartFile fotoTeste) {

        boolean resultado = reconhecimentoFacial.verificarFace(userId, fotoTeste);
        return ResponseEntity.ok(resultado);
    }
}
