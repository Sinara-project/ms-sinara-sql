package org.example.sinara.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.example.sinara.dto.request.ImagemRequestDTO;
import org.example.sinara.dto.response.ImagemResponseDTO;
import org.example.sinara.model.Imagem;
import org.example.sinara.repository.sql.ImagemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImagemService {
    private final ImagemRepository imagemRepository;

    public ImagemService(ImagemRepository imagemRepository) {
        this.imagemRepository = imagemRepository;
    }

    @Autowired
    private ObjectMapper objectMapper;

    private Imagem toEntity(ImagemRequestDTO dto) {
        return objectMapper.convertValue(dto, Imagem.class);
    }

    private ImagemResponseDTO toResponseDTO(Imagem imagem) {
        return objectMapper.convertValue(imagem, ImagemResponseDTO.class);
    }

    //Métod0 buscar por id
    public ImagemResponseDTO buscarPorId(Long id){
        Imagem imagem= imagemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Imagem com ID " + id + " não encontrada"));
        return toResponseDTO(imagem);
    }

    //Métod0 listar Imagem
    public List<ImagemResponseDTO> listarImagem(){
        return imagemRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    //Métod0 inserir Imagem
    public ImagemResponseDTO inserirImagem(ImagemRequestDTO dto) {
        Imagem imagem = toEntity(dto);
        Imagem salvo = imagemRepository.save(imagem);
        return toResponseDTO(salvo);
    }

    //Métod0 excluir Imagem
    public void excluirImagem(Long id) {
        if (!imagemRepository.existsById(id)) {
            throw new EntityNotFoundException("Imagem com ID " + id + " não encontrado");
        }
        imagemRepository.deleteById(id);
    }

    //Métod0 atualizar
    public ImagemResponseDTO atualizarImagem(Long id, ImagemRequestDTO dto) {
        Imagem imagem = imagemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Imagem com ID " + id + " não encontrado"));

        if (dto.getURL() != null) {
            imagem.setURL(dto.getURL());
        }
        if (dto.getIdOperario() != null) {
            imagem.setIdOperario(dto.getIdOperario());
        }

        Imagem atualizado = imagemRepository.save(imagem);
        return toResponseDTO(atualizado);
    }
}
