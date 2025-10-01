package org.example.sinara.controller;

import jakarta.validation.groups.Default;
import org.example.sinara.dto.request.CartaoCreditoRequestDTO;
import org.example.sinara.dto.response.CartaoCreditoResponseDTO;
import org.example.sinara.service.CartaoCreditoService;
import org.example.sinara.validation.OnCreate;
import org.example.sinara.validation.OnPatch;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/CartaoCredito")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class CartaoCreditoController {

    private final CartaoCreditoService cartaoCreditoService;

    public CartaoCreditoController(CartaoCreditoService cartaoCreditoService) {
        this.cartaoCreditoService = cartaoCreditoService;
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
    public ResponseEntity<String> excluirCartaoCredito(@PathVariable Long id) {
        cartaoCreditoService.excluirCartaoCredito(id);
        return ResponseEntity.ok("Cartão de Credito excluido com sucesso!");
    }

//    @PutMapping("/atualizar/{id}")
//    public ResponseEntity<String> atualizarCartaoCredito(@PathVariable Long id,
//                                                   @Validated({OnCreate.class, Default.class}) @RequestBody CartaoCreditoRequestDTO dto) {
//        CartaoCreditoService.atualizarCartaoCredito(id, dto);
//        return ResponseEntity.ok("Produto atualizado com sucesso!");
//    }


    @PatchMapping("/atualizarParcial/{id}")
    public ResponseEntity<String> atualizarProdutoParcial(
            @PathVariable Long id,
            @Validated({OnPatch.class, Default.class}) @RequestBody CartaoCreditoRequestDTO dto) {
        cartaoCreditoService.atualizarCartaoCredito(id, dto);
        return ResponseEntity.status(HttpStatus.OK).body("Produto parcialmente atualizado com sucesso");
    }
}
