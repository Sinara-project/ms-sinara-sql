package org.example.sinara.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.example.sinara.dto.request.CartaoCreditoRequestDTO;
import org.example.sinara.dto.response.CartaoCreditoResponseDTO;
import org.example.sinara.exception.CartaoDuplicadoException;
import org.example.sinara.model.CartaoCredito;
import org.example.sinara.model.Empresa;
import org.example.sinara.repository.sql.CartaoCreditoRepository;
import org.example.sinara.repository.sql.EmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartaoCreditoService {

    private final CartaoCreditoRepository cartaoCreditoRepository;
    private final EmpresaRepository empresaRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    public CartaoCreditoService(CartaoCreditoRepository cartaoCreditoRepository,
                                EmpresaRepository empresaRepository) {
        this.cartaoCreditoRepository = cartaoCreditoRepository;
        this.empresaRepository = empresaRepository;
    }

    private CartaoCredito toEntity(CartaoCreditoRequestDTO dto) {
        CartaoCredito cartaoCredito = new CartaoCredito();

        cartaoCredito.setNumero(dto.getNumero());
        cartaoCredito.setNomeTitular(dto.getNomeTitular());
        cartaoCredito.setValidade(dto.getValidade());
        cartaoCredito.setCvv(dto.getCvv());

        if (dto.getIdEmpresa() != null) {
            Empresa empresa = empresaRepository.findById(dto.getIdEmpresa())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Empresa com ID " + dto.getIdEmpresa() + " não encontrada"));
            cartaoCredito.setEmpresa(empresa);
        }

        return cartaoCredito;
    }

    private CartaoCreditoResponseDTO toResponseDTO(CartaoCredito cartaoCredito) {
        CartaoCreditoResponseDTO dto = new CartaoCreditoResponseDTO();

        dto.setId(cartaoCredito.getId());
        dto.setNumero(cartaoCredito.getNumero());
        dto.setNomeTitular(cartaoCredito.getNomeTitular());
        dto.setValidade(cartaoCredito.getValidade());
        dto.setCvv(cartaoCredito.getCvv());

        if (cartaoCredito.getEmpresa() != null) {
            dto.setIdEmpresa(cartaoCredito.getEmpresa().getId());
        }

        return dto;
    }

    public CartaoCreditoResponseDTO listarPorId(Integer id) {
        CartaoCredito cartaoCredito = cartaoCreditoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cartão de crédito com ID " + id + " não encontrado"));
        return toResponseDTO(cartaoCredito);
    }


    public List<CartaoCreditoResponseDTO> listarCartaoCredito(){
        return cartaoCreditoRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public CartaoCreditoResponseDTO inserirCartaoCredito(CartaoCreditoRequestDTO dto) {
        if (cartaoCreditoRepository.existsByNumero(dto.getNumero())) {
            throw new CartaoDuplicadoException(dto.getNumero());
        }
        CartaoCredito cartaoCredito = toEntity(dto);
        CartaoCredito salvo = cartaoCreditoRepository.save(cartaoCredito);
        return toResponseDTO(salvo);
    }

    public void excluirCartaoCredito(Integer id) {
        if (!cartaoCreditoRepository.existsById(id)) {
            throw new EntityNotFoundException("Cartão de credito com ID " + id + " não encontrado");
        }
        cartaoCreditoRepository.deleteById(id);
    }

    public CartaoCreditoResponseDTO atualizarCartaoCredito(Integer id, CartaoCreditoRequestDTO dto) {
        CartaoCredito cartaoCredito = cartaoCreditoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cartão de Credito com ID " + id + " não encontrado"));

        if (dto.getNumero() != null) {
            cartaoCredito.setNumero(dto.getNumero());
        }
        if (dto.getNomeTitular() != null) {
            cartaoCredito.setNomeTitular(dto.getNomeTitular());
        }
        if (dto.getValidade() != null) {
            cartaoCredito.setValidade(dto.getValidade());
        }
        if (dto.getCvv() != null) {
            cartaoCredito.setCvv(dto.getCvv());
        }
        if (dto.getIdEmpresa() != null) {
            Empresa empresa = empresaRepository.findById(dto.getIdEmpresa())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Empresa com ID " + dto.getIdEmpresa() + " não encontrada"));
            cartaoCredito.setEmpresa(empresa);
        }

        CartaoCredito atualizado = cartaoCreditoRepository.save(cartaoCredito);
        return toResponseDTO(atualizado);
    }



}

