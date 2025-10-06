package org.example.sinara.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.example.sinara.dto.request.OperarioRequestDTO;
import org.example.sinara.dto.response.OperarioResponseDTO;
import org.example.sinara.model.Operario;
import org.example.sinara.repository.sql.OperarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OperarioService {
    private final OperarioRepository operarioRepository;

    public OperarioService(OperarioRepository operarioRepository) {
        this.operarioRepository = operarioRepository;
    }

    @Autowired
    private ObjectMapper objectMapper;

    private Operario toEntity(OperarioRequestDTO dto) {
        return objectMapper.convertValue(dto, Operario.class);
    }

    private OperarioResponseDTO toResponseDTO(Operario operario) {
        return objectMapper.convertValue(operario, OperarioResponseDTO.class);
    }

    //Métod0 buscar por id
    public OperarioResponseDTO buscarPorId(Long id){
        Operario operario= operarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Operário não encontrado"));
        return toResponseDTO(operario);
    }

    //Métod0 listar
    public List<OperarioResponseDTO> listarOperario(){
        return operarioRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    //Métod0 inserir
    public OperarioResponseDTO inserirOperario(OperarioRequestDTO dto) {
        Operario operario = toEntity(dto);
        Operario salvo = operarioRepository.save(operario);
        return toResponseDTO(salvo);
    }

    //Métod0 excluir
    public void excluirOperario(Long id) {
        if (!operarioRepository.existsById(id)) {
            throw new EntityNotFoundException("Operário com id " + id + " não encontrado");
        }
        operarioRepository.deleteById(id);
    }

    //Métod0 atualizar
    public OperarioResponseDTO atualizarOperario(Long id, OperarioRequestDTO dto) {
        Operario operario = operarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Operário com ID " + id + " não encontrado"));

        if (dto.getUrl() != null) {
            operario.setUrl(dto.getUrl());
        }
        if (dto.getImagemUrl() != null) {
            operario.setImagemUrl(dto.getImagemUrl());
        }
        if (dto.getCpf() != null) {
            operario.setCpf(dto.getCpf());
        }
        if (dto.getNome() != null) {
            operario.setNome(dto.getNome());
        }
        if (dto.getEmail() != null) {
            operario.setEmail(dto.getEmail());
        }
        if (dto.getCargo() != null) {
            operario.setCargo(dto.getCargo());
        }
        if (dto.getSetor() != null) {
            operario.setSetor(dto.getSetor());
        }
        if (dto.getHorasPrevistas() != null) {
            operario.setHorasPrevistas(dto.getHorasPrevistas());
        }
        if (dto.getFerias() != null) {
            operario.setFerias(dto.getFerias());
        }
        if (dto.getAtivo() != null) {
            operario.setAtivo(dto.getAtivo());
        }
        if (dto.getSenha() != null) {
            operario.setSenha(dto.getSenha());
        }

        Operario atualizado = operarioRepository.save(operario);
        return toResponseDTO(atualizado);
    }

    //    Métodos derivados

    public Operario buscarPorNome(String nome){
        Operario operario = operarioRepository.findByNome(nome);
        if (operario == null){
            throw new EntityNotFoundException("Não contém nenhum operário com este nome");
        }
        return operario;
    }

    public Operario buscarPorPontosRegistrados(int pontosRegistrados){
        Operario operario = operarioRepository.findByHorasPrevistas(pontosRegistrados);
//        if (operario == null){
//            throw new EntityNotFoundException("Não contém nenhum operário com este nome");
//        }
        return operario;
    }

}
