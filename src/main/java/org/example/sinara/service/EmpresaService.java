package org.example.sinara.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.example.sinara.dto.request.CartaoCreditoRequestDTO;
import org.example.sinara.dto.request.EmpresaRequestDTO;
import org.example.sinara.dto.response.CartaoCreditoResponseDTO;
import org.example.sinara.dto.response.EmpresaResponseDTO;
import org.example.sinara.model.CartaoCredito;
import org.example.sinara.model.Empresa;
import org.example.sinara.model.Planos;
import org.example.sinara.repository.sql.EmpresaRepository;
import org.example.sinara.repository.sql.PlanosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EmpresaService {
    private final EmpresaRepository empresaRepository;
    private final PlanosRepository planosRepository; // precisa para buscar o plano (FK)

    @Autowired
    public EmpresaService(
            EmpresaRepository empresaRepository,
            PlanosRepository planosRepository
    ) {
        this.empresaRepository = empresaRepository;
        this.planosRepository = planosRepository;
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
        empresa.setPlanoInicial(dto.getPlanoInicial());

        Planos plano = planosRepository.findById(dto.getIdPlano())
                .orElseThrow(() -> new RuntimeException("Plano não encontrado"));
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
        dto.setPlanoInicial(empresa.getPlanoInicial());
        dto.setIdPlano(empresa.getIdPlano().getId()); // pega apenas o ID da FK

        return dto;
    }

//    Métodos comuns

    //Metodo buscar por id
    public EmpresaResponseDTO buscarPorId(Long id){
        Empresa empresa = empresaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Empresa com ID " + id + " não encontrada"));
        return toResponseDTO(empresa);
    }

    //Metodo listar empresa
    public List<EmpresaResponseDTO> listarEmpresas(){
        return empresaRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    //Metodo inserir empresa
    public EmpresaResponseDTO inserirEmpresa(EmpresaRequestDTO dto) {
        Empresa empresa = toEntity(dto);

        String codigoGerado;
        do {
            codigoGerado = gerarCodigoAleatorio();
        } while (empresaRepository.existsByCodigo(codigoGerado)); // evita duplicados

        empresa.setCodigo(codigoGerado);

        Empresa salvo = empresaRepository.save(empresa);
        return toResponseDTO(salvo);
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


    //Metodo excluir empresa
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
        if (dto.getSenha() != null) {
            empresa.setSenha(dto.getSenha());
        }
        if (dto.getSenhaAreaRestrita() != null) {
            empresa.setSenhaAreaRestrita(dto.getSenhaAreaRestrita());
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
        if (dto.getPlanoInicial() != null) {
            empresa.setPlanoInicial(dto.getPlanoInicial());
        }

        Empresa atualizado = empresaRepository.save(empresa);
        return toResponseDTO(atualizado);
    }

//  Query
    public Map<String, Object> buscarPerfilEmpresaPorId(Long id) {
        Map<String, Object> perfil = empresaRepository.buscarPerfilPorId(id);
        if (perfil == null || perfil.isEmpty()) {
            throw new EntityNotFoundException("Empresa com ID " + id + " não encontrada");
        }
        return perfil;
    }
}
