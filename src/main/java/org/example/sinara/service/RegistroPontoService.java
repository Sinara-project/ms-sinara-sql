package org.example.sinara.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.example.sinara.dto.request.RegistroPontoRequestDTO;
import org.example.sinara.dto.response.RegistroPontoResponseDTO;
import org.example.sinara.model.Empresa;
import org.example.sinara.model.Operario;
import org.example.sinara.model.RegistroPonto;
import org.example.sinara.repository.sql.EmpresaRepository;
import org.example.sinara.repository.sql.OperarioRepository;
import org.example.sinara.repository.sql.RegistroPontoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RegistroPontoService {
    private final RegistroPontoRepository registroPontoRepository;
    private final EmpresaRepository empresaRepository;
    private final OperarioRepository operarioRepository;

    private ObjectMapper objectMapper;

    @Autowired
    public RegistroPontoService(
            RegistroPontoRepository registroPontoRepository,
            EmpresaRepository empresaRepository,
            OperarioRepository operarioRepository,
            ObjectMapper objectMapper
    ) {
        this.registroPontoRepository = registroPontoRepository;
        this.empresaRepository = empresaRepository;
        this.operarioRepository = operarioRepository;
        this.objectMapper = objectMapper;
    }

    private RegistroPonto toEntity(RegistroPontoRequestDTO dto) {
//        return objectMapper.convertValue(dto, RegistroPonto.class);
        RegistroPonto registro = new RegistroPonto();
        registro.setHorarioEntrada(dto.getHorarioEntrada());
        registro.setHorarioSaida(dto.getHorarioSaida());

        Operario operario = operarioRepository.findById(dto.getIdOperario())
                .orElseThrow(() -> new RuntimeException("Operário não encontrado"));
        registro.setIdOperario(operario);

        Empresa empresa = empresaRepository.findById(dto.getIdEmpresa())
                .orElseThrow(() -> new RuntimeException("Empresa não encontrada"));
        registro.setIdEmpresa(empresa);

        return registro;
    }

    private RegistroPontoResponseDTO toResponseDTO(RegistroPonto registroPonto) {
//        return objectMapper.convertValue(registroPonto, RegistroPontoResponseDTO.class);
        RegistroPontoResponseDTO dto = new RegistroPontoResponseDTO();
        dto.setId(registroPonto.getId());
        dto.setHorarioEntrada(registroPonto.getHorarioEntrada());
        dto.setHorarioSaida(registroPonto.getHorarioSaida());
        dto.setIdOperario(registroPonto.getIdOperario().getId()); // pega apenas o ID
        dto.setIdEmpresa(registroPonto.getIdEmpresa().getId());   // pega apenas o ID
        return dto;
    }


    //Métod0 buscar por id
    public RegistroPontoResponseDTO buscarPorId(Long id){
        RegistroPonto registroPonto= registroPontoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Registro de ponto não encontrado"));
        return toResponseDTO(registroPonto);
    }

    //Métod0 listar
    public List<RegistroPontoResponseDTO> listarRegistroPonto(){
        return registroPontoRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    //Métod0 inserir
    public RegistroPontoResponseDTO inserirRegistroPonto(RegistroPontoRequestDTO dto) {
        RegistroPonto registroPonto = toEntity(dto);
        RegistroPonto salvo = registroPontoRepository.save(registroPonto);
        return toResponseDTO(salvo);
    }
    //Métod0 excluir
    public void excluirRegistroPonto(Long id) {
        if (!registroPontoRepository.existsById(id)) {
            throw new EntityNotFoundException("Registro de ponto não encontrado");
        }
        registroPontoRepository.deleteById(id);
    }

    //Métod0 atualizar
    public RegistroPontoResponseDTO atualizarRegistroPonto(Long id, RegistroPontoRequestDTO dto) {
        RegistroPonto registroPonto = registroPontoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Registro de ponto não encontrado"));

        if (dto.getHorarioEntrada() != null) {
            registroPonto.setHorarioEntrada(dto.getHorarioEntrada());
        }
        if (dto.getHorarioSaida() != null) {
            registroPonto.setHorarioSaida(dto.getHorarioSaida());
        }
        if (dto.getIdOperario() != null) {
            Operario operario = operarioRepository.findById(dto.getIdOperario())
                    .orElseThrow(() -> new RuntimeException("Operário não encontrado"));
            registroPonto.setIdOperario(operario);
        }

        if (dto.getIdEmpresa() != null) {
            Empresa empresa = empresaRepository.findById(dto.getIdEmpresa())
                    .orElseThrow(() -> new RuntimeException("Empresa não encontrada"));
            registroPonto.setIdEmpresa(empresa);
        }


        RegistroPonto atualizado = registroPontoRepository.save(registroPonto);
        return toResponseDTO(atualizado);
    }

    //    Métodos derivados

    public RegistroPonto buscarPorHorarioEntrada(LocalDateTime horarioEntrada){
        RegistroPonto registroPonto = registroPontoRepository.findByHorarioEntrada(horarioEntrada);
        if (registroPonto == null){
            throw new EntityNotFoundException("Operário ainda não bateu o ponto de entrada");
        }
        return registroPonto;
    }

    public RegistroPonto buscarPorHorarioSaida(LocalDateTime horarioSaida){
        RegistroPonto registroPonto = registroPontoRepository.findByHorarioSaida(horarioSaida);
        if (registroPonto == null){
            throw new EntityNotFoundException("Operário ainda não bateu o ponto de saída");
        }
        return registroPonto;
    }
}
