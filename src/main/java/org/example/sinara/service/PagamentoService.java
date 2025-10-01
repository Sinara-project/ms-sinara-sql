package org.example.sinara.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.example.sinara.dto.request.OperarioRequestDTO;
import org.example.sinara.dto.request.PagamentoRequestDTO;
import org.example.sinara.dto.response.OperarioResponseDTO;
import org.example.sinara.dto.response.PagamentoResponseDTO;
import org.example.sinara.model.Operario;
import org.example.sinara.model.Pagamento;
import org.example.sinara.repository.sql.OperarioRepository;
import org.example.sinara.repository.sql.PagamantoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PagamentoService {
    private final PagamantoRepository pagamentoRepository;

    public PagamentoService(PagamantoRepository pagamentoRepository) {
        this.pagamentoRepository = pagamentoRepository;
    }

    @Autowired
    private ObjectMapper objectMapper;

    private Pagamento toEntity(PagamentoRequestDTO dto) {
        return objectMapper.convertValue(dto, Pagamento.class);
    }

    private PagamentoResponseDTO toResponseDTO(Pagamento pagamento) {
        return objectMapper.convertValue(pagamento, PagamentoResponseDTO.class);
    }

    //Métod0 buscar por id
    public PagamentoResponseDTO buscarPorId(Long id){
        Pagamento pagamento= pagamentoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pagamento não encontrado"));
        return toResponseDTO(pagamento);
    }

    //Métod0 listar
    public List<PagamentoResponseDTO> listarPagamento(){
        return pagamentoRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    //Métod0 inserir
    public PagamentoResponseDTO inserirPagamento(PagamentoRequestDTO dto) {
        Pagamento pagamento = toEntity(dto);
        Pagamento salvo = pagamentoRepository.save(pagamento);
        return toResponseDTO(salvo);
    }

    //Métod0 excluir
    public void excluirPagamento(Long id) {
        if (!pagamentoRepository.existsById(id)) {
            throw new EntityNotFoundException("Pagamento com id " + id + " não encontrado");
        }
        pagamentoRepository.deleteById(id);
    }

    //Métod0 atualizar
    public PagamentoResponseDTO atualizarPagamento(Long id, PagamentoRequestDTO dto) {
        Pagamento pagamento = pagamentoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pagamento com ID " + id + " não encontrado"));

        if (dto.getValor() != null) {
            pagamento.setValor(dto.getValor());
        }
        if (dto.getData() != null) {
            pagamento.setData(dto.getData());
        }
        if (dto.getStatus() != null) {
            pagamento.setStatus(dto.getStatus());
        }
//        if (dto.getIdCartaoCredito() != null) {
//            pagamento.setIdCartaoCredito(dto.getIdCartaoCredito());
//        }
        //    private int idEmpresa;

        Pagamento atualizado = pagamentoRepository.save(pagamento);
        return toResponseDTO(atualizado);
    }
}
