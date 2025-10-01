package org.example.sinara.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.example.sinara.dto.request.CartaoCreditoRequestDTO;
import org.example.sinara.dto.request.EmpresaRequestDTO;
import org.example.sinara.dto.response.CartaoCreditoResponseDTO;
import org.example.sinara.dto.response.EmpresaResponseDTO;
import org.example.sinara.model.CartaoCredito;
import org.example.sinara.model.Empresa;
import org.example.sinara.repository.sql.EmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpresaService {
    private final EmpresaRepository empresaRepository;

    public EmpresaService(EmpresaRepository empresaRepository) {
        this.empresaRepository = empresaRepository;
    }

    @Autowired
    private ObjectMapper objectMapper;

    private Empresa toEntity(EmpresaRequestDTO dto) {
        return objectMapper.convertValue(dto, Empresa.class);
    }

    private EmpresaResponseDTO toResponseDTO(Empresa empresa) {
        return objectMapper.convertValue(empresa, EmpresaResponseDTO.class);
    }

//    Métodos comuns

    //Métod0 buscar por id
    public EmpresaResponseDTO buscarPorId(Long id){
        Empresa empresa = empresaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Empresa com ID " + id + " não encontrada"));
        return toResponseDTO(empresa);
    }

    //Métod0 listar empresa
    public List<EmpresaResponseDTO> listarEmpresas(){
        return empresaRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    //Métod0 inserir empresa
    public EmpresaResponseDTO inserirEmpresa(EmpresaRequestDTO dto) {
        Empresa empresa = toEntity(dto);
        Empresa salvo = empresaRepository.save(empresa);
        return toResponseDTO(salvo);
    }

    //Métod0 excluir empresa
    public void excluirEmpresa(Long id) {
        if (!empresaRepository.existsById(id)) {
            throw new EntityNotFoundException("Empresa com ID " + id + " não encontrado");
        }
        empresaRepository.deleteById(id);
    }

    public EmpresaResponseDTO atualizarEmpresa(Long id, EmpresaRequestDTO dto) {
        Empresa empresa = empresaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cartão de Credito com ID " + id + " não encontrado"));

        if (dto.getCnpj() != null) {
            empresa.setCnpj(dto.getCnpj());
        }
        if (dto.getNome() != null) {
            empresa.setNome(dto.getNome());
        }
        if (dto.getEmailCorporativo() != null) {
            empresa.setEmailCorporativo(dto.getEmailCorporativo());
        }
        if (dto.getRamoAtuacao() != null) {
            empresa.setRamoAtuacao(dto.getRamoAtuacao());
        }
        if (dto.getTelefone() != null) {
            empresa.setTelefone(dto.getTelefone());
        }
        if (dto.getPlanoInicial() != null) {
            empresa.setPlanoInicial(dto.getPlanoInicial());
        }

        Empresa atualizado = empresaRepository.save(empresa);
        return toResponseDTO(atualizado);
    }

//    Métodos derivados

    public Empresa buscarPorCnpj(String cnpj){
        Empresa empresa = empresaRepository.findByCnpj(cnpj);
        if (empresa == null){
            throw new EntityNotFoundException("Não contém nenhuma empresa com este CNPJ");
        }
        return empresa;
    }

    public Empresa buscarPorNome(String nome){
        Empresa empresa = empresaRepository.findByNome(nome);
        if (empresa == null){
            throw new EntityNotFoundException("Não contém nenhuma empresa com este nome");
        }
        return empresa;
    }

    public Empresa buscarPorEmailCorporativo(String emailCorporativo){
        Empresa empresa = empresaRepository.findByEmailCorporativo(emailCorporativo);
        if (empresa == null){
            throw new EntityNotFoundException("Não contém nenhuma empresa com este e-mail");
        }
        return empresa;
    }

    public Empresa buscarPorRamoAtuacao(String ramoAtuacao){
        Empresa empresa = empresaRepository.findByRamoAtuacaoLikeIgnoreCase(ramoAtuacao);
        if (empresa == null){
            throw new EntityNotFoundException("Não contém nenhuma empresa com este ramo de atuação");
        }
        return empresa;
    }

}
