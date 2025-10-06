package org.example.sinara.controller;

import jakarta.validation.groups.Default;
import org.example.sinara.dto.request.PagamentoRequestDTO;
import org.example.sinara.dto.response.PagamentoResponseDTO;
import org.example.sinara.open_api.PagamentoOpenApi;
import org.example.sinara.service.PagamentoService;
import org.example.sinara.validation.OnCreate;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pagamento")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class PagamentoController implements PagamentoOpenApi {
    private final PagamentoService pagamentoService;

    public PagamentoController(PagamentoService pagamentoService) {
        this.pagamentoService = pagamentoService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<PagamentoResponseDTO> buscarPagamentoPorId(@PathVariable Long id) {
        PagamentoResponseDTO pagamento = pagamentoService.buscarPorId(id);
        return ResponseEntity.ok(pagamento);
    }

    @GetMapping("/listar")
    public List<PagamentoResponseDTO> listarPagamento(){
        return pagamentoService.listarPagamento();
    }

    @PostMapping("/inserir")
    public ResponseEntity<String> inserirPagamento(@Validated({OnCreate.class, Default.class})
                                                       @RequestBody PagamentoRequestDTO dto) {
        pagamentoService.inserirPagamento(dto);
        return ResponseEntity.ok("Pagamento inserido com sucesso!");
    }

    @DeleteMapping("/excluir/{id}")
    public ResponseEntity<String> excluirPagamento(@PathVariable Long id) {
        pagamentoService.excluirPagamento(id);
        return ResponseEntity.ok("Pagamento excluído com sucesso!");
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<String> atualizarPagamento(@PathVariable Long id,
                                                    @Validated({OnCreate.class, Default.class}) @RequestBody PagamentoRequestDTO dto) {
        pagamentoService.atualizarPagamento(id, dto);
        return ResponseEntity.ok("Pagamento atualizado com sucesso!");
    }
}
