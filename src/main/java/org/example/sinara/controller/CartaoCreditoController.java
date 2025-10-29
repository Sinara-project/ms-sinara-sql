package org.example.sinara.controller;

import jakarta.validation.groups.Default;
import org.example.sinara.dto.request.CartaoCreditoRequestDTO;
import org.example.sinara.dto.response.CartaoCreditoResponseDTO;
import org.example.sinara.dto.response.EmpresaResponseDTO;
import org.example.sinara.open_api.CartaoCreditoOpenApi;
import org.example.sinara.service.CartaoCreditoService;
import org.example.sinara.validation.OnCreate;
import org.example.sinara.validation.OnPatch;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/user/CartaoCredito")
public class CartaoCreditoController implements CartaoCreditoOpenApi {

    private final CartaoCreditoService cartaoCreditoService;

    public CartaoCreditoController(CartaoCreditoService cartaoCreditoService) {
        this.cartaoCreditoService = cartaoCreditoService;
    }

//    Métodos comuns
    @GetMapping("buscarPorId/{id}")
    public ResponseEntity<CartaoCreditoResponseDTO> buscarCartaoCreditoPorId(@PathVariable Integer id) {
        CartaoCreditoResponseDTO cartaoCredito = cartaoCreditoService.listarPorId(id);
        return ResponseEntity.ok(cartaoCredito);
    }

    @GetMapping("/listar")
    public List<CartaoCreditoResponseDTO> listarCartaoCredito(){
        return cartaoCreditoService.listarCartaoCredito();
    }

    @PostMapping("/inserir")
    public ResponseEntity<String> inserirCartaoCredito(@Validated({OnCreate.class, Default.class}) @RequestBody CartaoCreditoRequestDTO dto) {
        cartaoCreditoService.inserirCartaoCredito(dto);
        return ResponseEntity.ok("Cartão de crédito inserido com sucesso!");
    }

    @DeleteMapping("/excluir/{id}")
    public ResponseEntity<String> excluirCartaoCredito(@PathVariable Integer id) {
        cartaoCreditoService.excluirCartaoCredito(id);
        return ResponseEntity.ok("Cartão de Credito excluido com sucesso!");
    }

    @PatchMapping("/atualizar/{id}")
    public ResponseEntity<String> atualizarProdutoParcial(
            @PathVariable Integer id,
            @Validated({OnPatch.class, Default.class}) @RequestBody CartaoCreditoRequestDTO dto) {
        cartaoCreditoService.atualizarCartaoCredito(id, dto);
        return ResponseEntity.status(HttpStatus.OK).body("Produto parcialmente atualizado com sucesso");
    }

//    Function
    @GetMapping("/validarCartaoCredito")
    public ResponseEntity<Boolean> validarCartao(
            @RequestParam String numero,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate validade) {

        Boolean valido = cartaoCreditoService.validarCartao(numero, validade);
        return ResponseEntity.ok(valido);
    }
}
