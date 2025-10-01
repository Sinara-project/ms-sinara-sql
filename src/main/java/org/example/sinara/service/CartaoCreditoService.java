package org.example.sinara.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.example.sinara.dto.request.CartaoCreditoRequestDTO;
import org.example.sinara.dto.response.CartaoCreditoResponseDTO;
import org.example.sinara.model.CartaoCredito;
import org.example.sinara.repository.sql.CartaoCreditoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartaoCreditoService {

    private final CartaoCreditoRepository cartaoCreditoRepository;

    public CartaoCreditoService(CartaoCreditoRepository cartaoCreditoRepository) {
        this.cartaoCreditoRepository = cartaoCreditoRepository;
    }

    @Autowired
    private ObjectMapper objectMapper;

    private CartaoCredito toEntity(CartaoCreditoRequestDTO dto) {
        return objectMapper.convertValue(dto, CartaoCredito.class);
    }

    private CartaoCreditoResponseDTO toResponseDTO(CartaoCredito cartaoCredito) {
        return objectMapper.convertValue(cartaoCredito, CartaoCreditoResponseDTO.class);
    }

    public CartaoCreditoResponseDTO listarPorId(Long id) {
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
        CartaoCredito cartaoCredito = toEntity(dto);
        CartaoCredito salvo = cartaoCreditoRepository.save(cartaoCredito);
        return toResponseDTO(salvo);
    }

    public void excluirCartaoCredito(Long id) {
        if (!cartaoCreditoRepository.existsById(id)) {
            throw new EntityNotFoundException("Cartão de credito com ID " + id + " não encontrado");
        }
        cartaoCreditoRepository.deleteById(id);
    }

    public CartaoCreditoResponseDTO atualizarCartaoCredito(Long id, CartaoCreditoRequestDTO dto) {
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
//        if (dto.getIdEmpresa() != null) {
//            cartaoCredito.setIdEmpresa(dto.getIdEmpresa());
//        }

        CartaoCredito atualizado = cartaoCreditoRepository.save(cartaoCredito);
        return toResponseDTO(atualizado);
    }



}

