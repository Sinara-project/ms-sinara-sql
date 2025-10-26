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
import org.example.sinara.utils.HttpClientPython;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class OperarioService {
    private final OperarioRepository operarioRepository;
    private final EmpresaRepository empresaRepository;
    private ObjectMapper objectMapper;
    private static final String RECONHECIMENTO_DIR = "uploads/reconhecimento";
    private final HttpClientPython httpClientPython;

    @Autowired
    public OperarioService(
            OperarioRepository operarioRepository,
            EmpresaRepository empresaRepository,
            ObjectMapper objectMapper,
            HttpClientPython httpClientPython
    ) {
        this.operarioRepository = operarioRepository;
        this.empresaRepository = empresaRepository;
        this.objectMapper = objectMapper;
        this.httpClientPython = httpClientPython;
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

//    Reconhecimento facil

    @Transactional
    public void atualizarFotoReconhecimento(Integer idOperario, MultipartFile file) {
        Operario operario = operarioRepository.findById(idOperario)
                .orElseThrow(() -> new EntityNotFoundException("Operário não encontrado"));

        try {
            // Garante que a pasta exista
            Path dir = Paths.get(RECONHECIMENTO_DIR);
            if (!Files.exists(dir)) {
                Files.createDirectories(dir);
            }

            // Salva o arquivo localmente
            String fileName = "operario_" + idOperario + "_" + LocalDateTime.now().toString().replace(":", "-") + ".jpg";
            Path filePath = dir.resolve(fileName);
            Files.write(filePath, file.getBytes());

            // Atualiza no banco a URL local
            operario.setUrl(filePath.toString());
            operarioRepository.save(operario);

        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar imagem de reconhecimento facial", e);
        }
    }

    public boolean verificarRosto(Integer idOperario) {
        Operario operario = operarioRepository.findById(idOperario)
                .orElseThrow(() -> new EntityNotFoundException("Operário não encontrado"));

        if (operario.getUrl() == null) {
            throw new RuntimeException("Operário não possui imagem de reconhecimento cadastrada.");
        }

        return httpClientPython.chamarVerificacaoFacial(idOperario, operario.getUrl());
    }

}
