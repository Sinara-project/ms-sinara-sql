package org.example.sinara.service;

import jakarta.persistence.EntityNotFoundException;
import org.example.sinara.dto.request.PagamentoRequestDTO;
import org.example.sinara.dto.response.PagamentoResponseDTO;
import org.example.sinara.model.CartaoCredito;
import org.example.sinara.model.Empresa;
import org.example.sinara.model.Pagamento;
import org.example.sinara.repository.sql.CartaoCreditoRepository;
import org.example.sinara.repository.sql.EmpresaRepository;
import org.example.sinara.repository.sql.PagamantoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PagamentoService {
    private final PagamantoRepository pagamentoRepository;
    private final EmpresaRepository empresaRepository;
    private final CartaoCreditoRepository cartaoCreditoRepository;

    @Autowired
    public PagamentoService(
            PagamantoRepository pagamentoRepository,
            EmpresaRepository empresaRepository,
            CartaoCreditoRepository cartaoCreditoRepository
    ) {
        this.pagamentoRepository = pagamentoRepository;
        this.empresaRepository = empresaRepository;
        this.cartaoCreditoRepository = cartaoCreditoRepository;
    }

    private Pagamento toEntity(PagamentoRequestDTO dto) {
        Pagamento pagamento = new Pagamento();

        pagamento.setValor(dto.getValor());
        pagamento.setDataPagamento(dto.getDataPagamento());
        pagamento.setStatus(dto.getStatus());

        Empresa empresa = empresaRepository.findById(dto.getIdEmpresa())
                .orElseThrow(() -> new EntityNotFoundException("Empresa com ID " + dto.getIdEmpresa() + " não encontrada"));
        pagamento.setEmpresa(empresa);

        // Busca cartão de crédito pelo ID
        CartaoCredito cartao = cartaoCreditoRepository.findById(dto.getIdCartaoCredito())
                .orElseThrow(() -> new EntityNotFoundException("Cartão de crédito com ID " + dto.getIdCartaoCredito() + " não encontrado"));
        pagamento.setCartaoCredito(cartao);

        return pagamento;
    }

    private PagamentoResponseDTO toResponseDTO(Pagamento pagamento) {
        PagamentoResponseDTO dto = new PagamentoResponseDTO();

        dto.setId(pagamento.getId());
        dto.setValor(pagamento.getValor());
        dto.setDataPagamento(pagamento.getDataPagamento());
        dto.setStatus(pagamento.getStatus());

        // Retorna apenas os IDs das FKs
        if (pagamento.getEmpresa() != null) {
            dto.setIdEmpresa(pagamento.getEmpresa().getId());
        }

        if (pagamento.getCartaoCredito() != null) {
            dto.setIdCartaoCredito(pagamento.getCartaoCredito().getId());
        }

        return dto;
    }

    //Métodos comuns
    public PagamentoResponseDTO buscarPorId(Integer id){
        Pagamento pagamento= pagamentoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pagamento não encontrado"));
        return toResponseDTO(pagamento);
    }

    public List<PagamentoResponseDTO> listarPagamento(){
        return pagamentoRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public PagamentoResponseDTO inserirPagamento(PagamentoRequestDTO dto) {
        Pagamento pagamento = toEntity(dto);
        Pagamento salvo = pagamentoRepository.save(pagamento);
        return toResponseDTO(salvo);
    }

    public void excluirPagamento(Integer id) {
        if (!pagamentoRepository.existsById(id)) {
            throw new EntityNotFoundException("Pagamento com id " + id + " não encontrado");
        }
        pagamentoRepository.deleteById(id);
    }

    public PagamentoResponseDTO atualizarPagamento(Integer id, PagamentoRequestDTO dto) {
        Pagamento pagamento = pagamentoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pagamento com ID " + id + " não encontrado"));

        if (dto.getValor() != null) {
            pagamento.setValor(dto.getValor());
        }
        if (dto.getDataPagamento() != null) {
            pagamento.setDataPagamento(dto.getDataPagamento());
        }
        if (dto.getStatus() != null) {
            pagamento.setStatus(dto.getStatus());
        }
        if (dto.getIdCartaoCredito() != null) {
            CartaoCredito cartao = cartaoCreditoRepository.findById(dto.getIdCartaoCredito())
                    .orElseThrow(() -> new EntityNotFoundException("Cartão de crédito não encontrado"));
            pagamento.setCartaoCredito(cartao);
        }

        if (dto.getIdEmpresa() != null) {
            Empresa empresa = empresaRepository.findById(dto.getIdEmpresa())
                    .orElseThrow(() -> new EntityNotFoundException("Empresa não encontrada"));
            pagamento.setEmpresa(empresa);
        }

        Pagamento atualizado = pagamentoRepository.save(pagamento);
        return toResponseDTO(atualizado);
    }
}
