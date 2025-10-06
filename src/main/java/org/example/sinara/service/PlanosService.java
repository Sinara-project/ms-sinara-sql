package org.example.sinara.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.example.sinara.dto.request.PlanosRequestDTO;
import org.example.sinara.dto.response.PlanosResponseDTO;
import org.example.sinara.model.Planos;
import org.example.sinara.repository.sql.PlanosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlanosService {
    private final PlanosRepository planosRepository;

    public PlanosService(PlanosRepository planosRepository) {
        this.planosRepository = planosRepository;
    }

    @Autowired
    private ObjectMapper objectMapper;

    private Planos toEntity(PlanosRequestDTO dto) {
        return objectMapper.convertValue(dto, Planos.class);
    }

    private PlanosResponseDTO toResponseDTO(Planos planos) {
        return objectMapper.convertValue(planos, PlanosResponseDTO.class);
    }

    //Métod0 buscar por id
    public PlanosResponseDTO buscarPorId(Long id){
        Planos planos= planosRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Plano não encontrado"));
        return toResponseDTO(planos);
    }

    //Métod0 listar
    public List<PlanosResponseDTO> listarPlanos(){
        return planosRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    //Métod0 inserir
    public PlanosResponseDTO inserirPlanos(PlanosRequestDTO dto) {
        Planos planos = toEntity(dto);
        Planos salvo = planosRepository.save(planos);
        return toResponseDTO(salvo);
    }

    //Métod0 excluir
    public void excluirPlanos(Long id) {
        if (!planosRepository.existsById(id)) {
            throw new EntityNotFoundException("Planos com id " + id + " não encontrado");
        }
        planosRepository.deleteById(id);
    }

    //Métod0 atualizar
    public PlanosResponseDTO atualizarPlanos(Long id, PlanosRequestDTO dto) {
        Planos planos = planosRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Plano com ID " + id + " não encontrado"));

        if (dto.getNome() != null) {
            planos.setNome(dto.getNome());
        }
        if (dto.getPrecoMensal() != null) {
            planos.setPrecoMensal(dto.getPrecoMensal());
        }
        if (dto.getPrecoAnual() != null) {
            planos.setPrecoAnual(dto.getPrecoAnual());
        }
        if (dto.getRecursos() != null) {
            planos.setRecursos(dto.getRecursos());
        }

        Planos atualizado = planosRepository.save(planos);
        return toResponseDTO(atualizado);
    }
}
