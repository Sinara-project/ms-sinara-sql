package org.example.sinara.controller;

import jakarta.validation.groups.Default;
import org.example.sinara.dto.request.ImagemRequestDTO;
import org.example.sinara.dto.response.ImagemResponseDTO;
import org.example.sinara.service.ImagemService;
import org.example.sinara.validation.OnCreate;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/imagem")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class ImagemController {

    private final ImagemService imagemService;

    public ImagemController(ImagemService imagemService) {
        this.imagemService = imagemService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ImagemResponseDTO> buscarImagemPorId(@PathVariable Long id) {
        ImagemResponseDTO imagem = imagemService.buscarPorId(id);
        return ResponseEntity.ok(imagem);
    }

    @GetMapping("/listar")
    public List<ImagemResponseDTO> listarImagem(){
        return imagemService.listarImagem();
    }

    @PostMapping("/inserir")
    public ResponseEntity<String> inserirImagem(@Validated({OnCreate.class, Default.class}) @RequestBody ImagemRequestDTO dto) {
        imagemService.inserirImagem(dto);
        return ResponseEntity.ok("Imagem inserido com sucesso!");
    }

    @DeleteMapping("/excluir/{id}")
    public ResponseEntity<String> excluirImagem(@PathVariable Long id) {
        imagemService.excluirImagem(id);
        return ResponseEntity.ok("Imagem excluída com sucesso!");
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<String> atualizarImagem(@PathVariable Long id,
                                                   @Validated({OnCreate.class, Default.class}) @RequestBody ImagemRequestDTO dto) {
        imagemService.atualizarImagem(id, dto);
        return ResponseEntity.ok("Imagem atualizado com sucesso!");
    }
}
