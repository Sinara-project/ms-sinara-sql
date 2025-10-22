package org.example.sinara.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.example.sinara.dto.request.OperarioRequestDTO;
import org.example.sinara.dto.response.OperarioResponseDTO;
import org.example.sinara.exception.CpfDuplicadoException;
import org.example.sinara.exception.EmailDuplicadoException;
import org.example.sinara.model.Empresa;
import org.example.sinara.model.Operario;
import org.example.sinara.repository.sql.EmpresaRepository;
import org.example.sinara.repository.sql.OperarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OperarioService {
    private final OperarioRepository operarioRepository;
    private final EmpresaRepository empresaRepository;
    private ObjectMapper objectMapper;

    @Autowired
    public OperarioService(
            OperarioRepository operarioRepository,
            EmpresaRepository empresaRepository,
            ObjectMapper objectMapper
    ) {
        this.operarioRepository = operarioRepository;
        this.empresaRepository = empresaRepository;
        this.objectMapper = objectMapper;
    }


    private Operario toEntity(OperarioRequestDTO dto) {
        Operario operario = new Operario();

        // Mapeia os atributos simples
        operario.setUrl(dto.getUrl());
        operario.setImagemUrl(dto.getImagemUrl()); // Corrigido: campo no model é "imagemUrl"
        operario.setCpf(dto.getCpf());
        operario.setNome(dto.getNome());
        operario.setEmail(dto.getEmail());
        operario.setCargo(dto.getCargo());
        operario.setSetor(dto.getSetor());
        operario.setFerias(dto.getFerias());
        operario.setAtivo(dto.getAtivo());
        operario.setSenha(dto.getSenha());
        dto.setHorasPrevistas(operario.getHorasPrevistas());

        // Associa empresa (FK)
        Empresa empresa = empresaRepository.findById(dto.getIdEmpresa())
                .orElseThrow(() -> new RuntimeException("Empresa não encontrada"));
        operario.setIdEmpresa(empresa);

        return operario;
    }

    private OperarioResponseDTO toResponseDTO(Operario operario) {
        OperarioResponseDTO dto = new OperarioResponseDTO();

        dto.setId(operario.getId());
        dto.setUrl(operario.getUrl());
        dto.setImagemUrl(operario.getImagemUrl());
        dto.setCpf(operario.getCpf());
        dto.setNome(operario.getNome());
        dto.setEmail(operario.getEmail());
        dto.setCargo(operario.getCargo());
        dto.setSetor(operario.getSetor());
        dto.setFerias(operario.getFerias());
        dto.setAtivo(operario.getAtivo());
        dto.setSenha(operario.getSenha());
        dto.setHorasPrevistas(operario.getHorasPrevistas());
        dto.setIdEmpresa(operario.getIdEmpresa().getId()); // pega só o ID da empresa

        return dto;
    }

    //Métod0 buscar por id
    public OperarioResponseDTO buscarPorId(Integer id){
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
        if (operarioRepository.existsByCpf(dto.getCpf())) {
            throw new CpfDuplicadoException(dto.getCpf());
        }
        if (operarioRepository.existsByEmail(dto.getEmail())) {
            throw new EmailDuplicadoException(dto.getEmail());
        }

        Operario operario = toEntity(dto);
        Operario salvo = operarioRepository.save(operario);
        return toResponseDTO(salvo);
    }

    //Métod0 excluir
    public void excluirOperario(Integer id) {
        if (!operarioRepository.existsById(id)) {
            throw new EntityNotFoundException("Operário com id " + id + " não encontrado");
        }
        operarioRepository.deleteById(id);
    }

    //Métod0 atualizar
    public OperarioResponseDTO atualizarOperario(Integer id, OperarioRequestDTO dto) {
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
        if (dto.getFerias() != null) {
            operario.setFerias(dto.getFerias());
        }
        if (dto.getAtivo() != null) {
            operario.setAtivo(dto.getAtivo());
        }
        if (dto.getSenha() != null) {
            operario.setSenha(dto.getSenha());
        }
        if (dto.getHorasPrevistas() != null) {
            operario.setHorasPrevistas(dto.getHorasPrevistas());
        }

        Operario atualizado = operarioRepository.save(operario);
        return toResponseDTO(atualizado);
    }

//  Query
    public Map<String, Object> buscarPerfilOperarioPorId(Integer id) {
        Map<String, Object> perfil = operarioRepository.buscarPerfilOperarioPorId(id);

        if (perfil == null || perfil.isEmpty()) {
            throw new EntityNotFoundException("Operário com ID " + id + " não encontrado");
        }

        return perfil;
    }


    public int getHorasPrevistas(Integer idOperario) {
        Integer horas = operarioRepository.findHorasPrevistasByOperario(idOperario);
        return horas != null ? horas : 0;
    }
}
