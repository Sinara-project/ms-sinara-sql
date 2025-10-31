package org.example.sinara.service;

import jakarta.persistence.EntityNotFoundException;
import org.example.sinara.dto.request.EmpresaRequestDTO;
import org.example.sinara.dto.request.OperarioLoginRequestDTO;
import org.example.sinara.dto.response.EmpresaLoginResponseDTO;
import org.example.sinara.dto.response.EmpresaResponseDTO;
import org.example.sinara.exception.CnpjDuplicadoException;
import org.example.sinara.exception.EmailDuplicadoException;
import org.example.sinara.exception.PlanoNaoEncontradoException;
import org.example.sinara.model.Empresa;
import org.example.sinara.model.Planos;
import org.example.sinara.repository.sql.EmpresaRepository;
import org.example.sinara.repository.sql.PlanosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import java.util.*;

@Service
public class EmpresaService {
    private final EmpresaRepository empresaRepository;
    private final PlanosRepository planosRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public EmpresaService(
            EmpresaRepository empresaRepository,
            PlanosRepository planosRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.empresaRepository = empresaRepository;
        this.planosRepository = planosRepository;
        this.passwordEncoder = passwordEncoder;
    }

    private Empresa toEntity(EmpresaRequestDTO dto) {
        Empresa empresa = new Empresa();

        empresa.setCnpj(dto.getCnpj());
        empresa.setNome(dto.getNome());
        empresa.setSenha(dto.getSenha());
        empresa.setSenhaAreaRestrita(dto.getSenhaAreaRestrita());
        empresa.setImagemUrl(dto.getImagemUrl());
        empresa.setEmail(dto.getEmail());
        empresa.setRamoAtuacao(dto.getRamoAtuacao());
        empresa.setTelefone(dto.getTelefone());

        Planos plano = planosRepository.findById(dto.getIdPlano())
                .orElseThrow(() -> new PlanoNaoEncontradoException(dto.getIdPlano()));
        empresa.setIdPlano(plano);

        return empresa;
    }

    private EmpresaResponseDTO toResponseDTO(Empresa empresa) {
        EmpresaResponseDTO dto = new EmpresaResponseDTO();

        dto.setId(empresa.getId());
        dto.setCnpj(empresa.getCnpj());
        dto.setNome(empresa.getNome());
        dto.setSenha(empresa.getSenha());
        dto.setSenhaAreaRestrita(empresa.getSenhaAreaRestrita());
        dto.setCodigo(empresa.getCodigo());
        dto.setImagemUrl(empresa.getImagemUrl());
        dto.setEmail(empresa.getEmail());
        dto.setRamoAtuacao(empresa.getRamoAtuacao());
        dto.setTelefone(empresa.getTelefone());
        dto.setIdPlano(empresa.getIdPlano().getId()); // pega apenas o ID da FK

        return dto;
    }

//    Métodos comuns
    public EmpresaResponseDTO buscarPorId(Integer id){
        Empresa empresa = empresaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Empresa com ID " + id + " não encontrada"));
        return toResponseDTO(empresa);
    }

    public List<EmpresaResponseDTO> listarEmpresas(){
        return empresaRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public EmpresaLoginResponseDTO inserirEmpresa(EmpresaRequestDTO dto) {
        if (empresaRepository.existsByCnpj(dto.getCnpj())) {
            throw new CnpjDuplicadoException(dto.getCnpj());
        }

        if (empresaRepository.existsByEmail(dto.getEmail())) {
            throw new EmailDuplicadoException(dto.getEmail());
        }

        Empresa empresa = toEntity(dto);

        String codigoGerado;
        do {
            codigoGerado = gerarCodigoAleatorio();
        } while (empresaRepository.existsByCodigo(codigoGerado));

        empresa.setCodigo(codigoGerado);
        empresa.setSenha(passwordEncoder.encode(dto.getSenha()));

        empresa.setCodigo(codigoGerado);
        empresa.setSenhaAreaRestrita(passwordEncoder.encode(dto.getSenhaAreaRestrita()));

        Empresa salva = empresaRepository.save(empresa);

        return new EmpresaLoginResponseDTO(
                salva.getId(),
                salva.getCnpj(),
                salva.getNome(),
                salva.getCodigo(),
                salva.getEmail(),
                salva.getRamoAtuacao(),
                salva.getImagemUrl()
        );
    }

    private String gerarCodigoAleatorio() {
        String letras = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String numeros = "0123456789";
        StringBuilder codigo = new StringBuilder();
        Random random = new Random();

        //letra
        for (int i = 0; i < 3; i++) {
            int index = random.nextInt(letras.length());
            codigo.append(letras.charAt(index));
        }

        //num
        for (int i = 0; i < 3; i++) {
            int index = random.nextInt(numeros.length());
            codigo.append(numeros.charAt(index));
        }
        List<Character> chars = new ArrayList<>();
        for (char c : codigo.toString().toCharArray()) {
            chars.add(c);
        }
        Collections.shuffle(chars);

        StringBuilder resultadoFinal = new StringBuilder();
        for (char c : chars) {
            resultadoFinal.append(c);
        }

        return resultadoFinal.toString();
    }

    public void excluirEmpresa(Integer id) {
        if (!empresaRepository.existsById(id)) {
            throw new EntityNotFoundException("Empresa com ID " + id + " não encontrado");
        }
        empresaRepository.deleteById(id);
    }

    public EmpresaResponseDTO atualizarEmpresa(Integer id, EmpresaRequestDTO dto) {
        Empresa empresa = empresaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cartão de Credito com ID " + id + " não encontrado"));

        if (dto.getCnpj() != null) {
            empresa.setCnpj(dto.getCnpj());
        }
        if (dto.getNome() != null) {
            empresa.setNome(dto.getNome());
        }
        if (dto.getSenha() != null) {
            empresa.setSenha(passwordEncoder.encode(dto.getSenha()));
        }
        if (dto.getSenhaAreaRestrita() != null) {
            empresa.setSenhaAreaRestrita(passwordEncoder.encode(dto.getSenhaAreaRestrita()));
        }
        if (dto.getImagemUrl() != null) {
            empresa.setImagemUrl(dto.getImagemUrl());
        }
        if (dto.getEmail() != null) {
            empresa.setEmail(dto.getEmail());
        }
        if (dto.getRamoAtuacao() != null) {
            empresa.setRamoAtuacao(dto.getRamoAtuacao());
        }
        if (dto.getTelefone() != null) {
            empresa.setTelefone(dto.getTelefone());
        }

        Empresa atualizado = empresaRepository.save(empresa);
        return toResponseDTO(atualizado);
    }

    public EmpresaResponseDTO atualizarSenhaAreaRestrita(Integer id, String novaSenha) {
        Empresa empresa = empresaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Empresa com ID " + id + " não encontrada"));

        String senhaCriptografada = passwordEncoder.encode(novaSenha);
        empresa.setSenhaAreaRestrita(senhaCriptografada);

        Empresa atualizado = empresaRepository.save(empresa);
        return toResponseDTO(atualizado);
    }

    //  Query
    public Map<String, Object> buscarPerfilEmpresaPorId(Integer id) {
        Map<String, Object> perfil = empresaRepository.buscarPerfilPorId(id);
        if (perfil == null || perfil.isEmpty()) {
            throw new EntityNotFoundException("Empresa com ID " + id + " não encontrada");
        }
        return perfil;
    }

    public Map<String, Object> obterIdEmpresaPorCnpj(String cnpj) {
        Map<String, Object> dados = empresaRepository.findIdByCnpj(cnpj);
        if (dados == null || dados.isEmpty()) {
            throw new EntityNotFoundException("Empresa com CNPJ " + cnpj + " não encontrada");
        }
        return dados;
    }

    public EmpresaLoginResponseDTO loginEmpresa(String cnpj, String senhaDigitada) {
        return empresaRepository.findByCnpj(cnpj)
                .map(empresa -> {
                    if (passwordEncoder.matches(senhaDigitada, empresa.getSenha())) {
                        return new EmpresaLoginResponseDTO(
                                empresa.getId(),
                                empresa.getCnpj(),
                                empresa.getNome(),
                                empresa.getCodigo(),
                                empresa.getEmail(),
                                empresa.getRamoAtuacao(),
                                empresa.getImagemUrl()
                        );
                    } else {
                        throw new RuntimeException("Senha incorreta");
                    }
                })
                .orElseThrow(() -> new RuntimeException("Empresa não encontrada"));
    }

    public boolean loginAreaRestrita(Integer idEmpresa, String senhaDigitada) {
        return empresaRepository.findById(idEmpresa)
                .map(empresa -> passwordEncoder.matches(senhaDigitada, empresa.getSenhaAreaRestrita()))
                .orElse(false);
    }

    //  function
    public void rebaixarPlanos() {
        try {
            empresaRepository.rebaixarPlanosPorInadimplencia();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao rebaixar planos: " + e.getMessage());
        }
    }

//    Procedure
    @Transactional
    public String mudarParaPremium(Integer empresaId, Integer cartaoId) {
        try {
            empresaRepository.mudarParaPremium(empresaId, cartaoId);
            return "Plano da empresa alterado com sucesso!";
        } catch (Exception e) {
            return "Erro ao mudar para Premium: " + e.getMessage();
        }
    }
}
