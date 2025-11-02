package org.example.sinara.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.example.sinara.dto.request.OperarioLoginRequestDTO;
import org.example.sinara.dto.request.OperarioRequestDTO;
import org.example.sinara.dto.response.EmpresaResponseDTO;
import org.example.sinara.dto.response.OperarioResponseDTO;
import org.example.sinara.exception.CpfDuplicadoException;
import org.example.sinara.exception.EmailDuplicadoException;
import org.example.sinara.exception.EmpresaNaoEncontradaException;
import org.example.sinara.model.Empresa;
import org.example.sinara.model.Operario;
import org.example.sinara.repository.sql.EmpresaRepository;
import org.example.sinara.repository.sql.OperarioRepository;
import org.example.sinara.utils.HttpClientPython;
import org.example.sinara.utils.ReconhecimentoFacial;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    private final ReconhecimentoFacial reconhecimentoFacial;
    private ObjectMapper objectMapper;
    private static final String RECONHECIMENTO_DIR = "uploads/reconhecimento";
    private final HttpClientPython httpClientPython;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public OperarioService(
            OperarioRepository operarioRepository,
            EmpresaRepository empresaRepository,
            ObjectMapper objectMapper,
            HttpClientPython httpClientPython,
            PasswordEncoder passwordEncoder,
            ReconhecimentoFacial reconhecimentoFacial) {
        this.operarioRepository = operarioRepository;
        this.empresaRepository = empresaRepository;
        this.objectMapper = objectMapper;
        this.httpClientPython = httpClientPython;
        this.passwordEncoder = passwordEncoder;
        this.reconhecimentoFacial = reconhecimentoFacial;
    }


    private Operario toEntity(OperarioRequestDTO dto) {
        Operario operario = new Operario();

        operario.setImagemUrl(dto.getImagemUrl());
        operario.setCpf(dto.getCpf());
        operario.setNome(dto.getNome());
        operario.setEmail(dto.getEmail());
        operario.setCargo(dto.getCargo());
        operario.setSetor(dto.getSetor());
        operario.setFerias(dto.getFerias());
        operario.setAtivo(dto.getAtivo());
        operario.setSenha(dto.getSenha());
        operario.setHorasPrevistas(dto.getHorasPrevistas());

        Empresa empresa = empresaRepository.findById(dto.getIdEmpresa())
                .orElseThrow(() -> new EmpresaNaoEncontradaException(dto.getIdEmpresa()));

        operario.setIdEmpresa(empresa);

        return operario;
    }

    private OperarioResponseDTO toResponseDTO(Operario operario) {
        OperarioResponseDTO dto = new OperarioResponseDTO();

        dto.setId(operario.getId());
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
        dto.setIdEmpresa(operario.getIdEmpresa().getId());

        return dto;
    }

    //Métodos comuns
    public OperarioResponseDTO buscarPorId(Integer id){
        Operario operario= operarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Operário não encontrado"));
        return toResponseDTO(operario);
    }

    public List<OperarioResponseDTO> listarOperario(){
        return operarioRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public OperarioResponseDTO inserirOperario(OperarioRequestDTO dto) {
        if (operarioRepository.existsByCpf(dto.getCpf())) {
            throw new CpfDuplicadoException(dto.getCpf());
        }
        if (operarioRepository.existsByEmail(dto.getEmail())) {
            throw new EmailDuplicadoException(dto.getEmail());
        }

        Operario operario = toEntity(dto);
        operario.setSenha(passwordEncoder.encode(dto.getSenha()));
        Operario salvo = operarioRepository.save(operario);
        return toResponseDTO(salvo);
    }

    public void excluirOperario(Integer id) {
        if (!operarioRepository.existsById(id)) {
            throw new EntityNotFoundException("Operário com id " + id + " não encontrado");
        }
        operarioRepository.deleteById(id);
    }

    public OperarioResponseDTO atualizarOperario(Integer id, OperarioRequestDTO dto) {
        Operario operario = operarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Operário com ID " + id + " não encontrado"));

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
            operario.setSenha(passwordEncoder.encode(dto.getSenha()));
        }
        if (dto.getHorasPrevistas() != null) {
            operario.setHorasPrevistas(dto.getHorasPrevistas());
        }

        Operario atualizado = operarioRepository.save(operario);
        return toResponseDTO(atualizado);
    }

    public boolean verificarSenha(Integer idOperario, String senhaDigitada) {
        Operario operario = operarioRepository.findById(idOperario)
                .orElseThrow(() -> new EntityNotFoundException("Operário com ID " + idOperario + " não encontrado"));

        return passwordEncoder.matches(senhaDigitada, operario.getSenha());
    }

    public OperarioResponseDTO atualizarSenha(Integer id, String novaSenha) {
        Operario operario = operarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Operario com ID " + id + " não encontradoa"));

        String senhaCriptografada = passwordEncoder.encode(novaSenha);
        operario.setSenha(senhaCriptografada);

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

    public String obterIdOperarioPorCpf(String cpf) {
        return operarioRepository.findIdByCpf(cpf);
    }

    public List<Map<String, Object>> buscarOperariosPorIdEmpresa(Integer idEmpresa) {
        List<Map<String, Object>> operarios = operarioRepository.buscarOperariosPorIdEmpresa(idEmpresa);
        if (operarios == null || operarios.isEmpty()) {
            throw new EntityNotFoundException("Nenhum operário encontrado para a empresa com ID " + idEmpresa);
        }
        return operarios;
    }

    public boolean validarLogin(OperarioLoginRequestDTO loginDTO) {
        return operarioRepository.findByLogin(
                        loginDTO.getEmail(),
                        loginDTO.getCpf(),
                        loginDTO.getCodigoEmpresa()
                ).map(operario -> passwordEncoder.matches(loginDTO.getSenha(), operario.getSenha()))
                .orElse(false);
    }

    //Procedure
    @Transactional
    public String atualizarStatus(Integer operarioId, Boolean ativo, Boolean ferias) {
        try {
            operarioRepository.atualizarStatus(operarioId, ativo, ferias);
            return "Status do funcionário atualizado com sucesso!";
        } catch (Exception e) {
            return "Erro ao atualizar status do funcionário: " + e.getMessage();
        }
    }

//    Reconhecimento facil

    @Transactional
    public void atualizarFotoReconhecimento(Integer idOperario, MultipartFile file) {
        Operario operario = operarioRepository.findById(idOperario)
                .orElseThrow(() -> new RuntimeException("Operário não encontrado."));

        // chama flask para upload no cloudinary
        String imageUrl = httpClientPython.uploadImagem(file);

        operario.setImagemUrl(imageUrl);
        operarioRepository.save(operario);
    }


    @PostMapping("/verificar-facial")
    public ResponseEntity<Boolean> verificarFacial(
            @RequestParam("userId") Integer userId,
            @RequestParam("fotoTeste") MultipartFile fotoTeste) {

        boolean resultado = reconhecimentoFacial.verificarFace(userId, fotoTeste);
        return ResponseEntity.ok(resultado);
    }
}
