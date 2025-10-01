package org.example.sinara.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.example.sinara.dto.request.PermissoesRequestDTO;
import org.example.sinara.dto.response.PermissoesResponseDTO;
import org.example.sinara.model.Permissoes;
import org.example.sinara.repository.sql.PermissoesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissoesService {
    private final PermissoesRepository permissoesRepository;

    public PermissoesService(PermissoesRepository permissoesRepository) {
        this.permissoesRepository = permissoesRepository;
    }

    @Autowired
    private ObjectMapper objectMapper;

    private Permissoes toEntity(PermissoesRequestDTO dto) {
        return objectMapper.convertValue(dto, Permissoes.class);
    }

    private PermissoesResponseDTO toResponseDTO(Permissoes permissoes) {
        return objectMapper.convertValue(permissoes, PermissoesResponseDTO.class);
    }

    //Métod0 buscar por id
    public PermissoesResponseDTO buscarPorId(Long id){
        Permissoes permissoes= permissoesRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Permissoes não encontrada"));
        return toResponseDTO(permissoes);
    }

    //Métod0 listar
    public List<PermissoesResponseDTO> listarPermissoes(){
        return permissoesRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    //Métod0 inserir
    public PermissoesResponseDTO inserirPermissoes(PermissoesRequestDTO dto) {
        Permissoes permissoes = toEntity(dto);
        Permissoes salvo = permissoesRepository.save(permissoes);
        return toResponseDTO(salvo);
    }

    //Métod0 excluir
    public void excluirPermissoes(Long id) {
        if (!permissoesRepository.existsById(id)) {
            throw new EntityNotFoundException("Permissoes com id " + id + " não encontradas");
        }
        permissoesRepository.deleteById(id);
    }

    //Métod0 atualizar
    public PermissoesResponseDTO atualizarPermissoes(Long id, PermissoesRequestDTO dto) {
        Permissoes permissoes = permissoesRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Permissoes com ID " + id + " não encontrada"));

        if (dto.getAdicionarUsuario() != null) {
            permissoes.setAdicionarUsuario(dto.getAdicionarUsuario());
        }
        if (dto.getResponderFormulario() != null) {
            permissoes.setResponderFormulario(dto.getResponderFormulario());
        }
        if (dto.getVisualizarDashboards() != null) {
            permissoes.setVisualizarDashboards(dto.getVisualizarDashboards());
        }
        if (dto.getCriarFormulario() != null) {
            permissoes.setCriarFormulario(dto.getCriarFormulario());
        }
        if (dto.getVisualizarFormulario() != null) {
            permissoes.setVisualizarFormulario(dto.getVisualizarFormulario());
        }
        if (dto.getEditarDadoOperario() != null) {
            permissoes.setEditarDadoOperario(dto.getEditarDadoOperario());
        }
        if (dto.getIdOperario() != null) {
            permissoes.setIdOperario(dto.getIdOperario());
        }

        Permissoes atualizado = permissoesRepository.save(permissoes);
        return toResponseDTO(atualizado);
    }
}
