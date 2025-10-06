package org.example.sinara.open_api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.example.sinara.dto.request.PagamentoRequestDTO;
import org.example.sinara.dto.response.PagamentoResponseDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PagamentoOpenApi {

    @Operation(
            summary = "Lista pagamento por ID",
            description = "Retorna um objeto contendo todas as informações do pagamento pelo seu ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pagamento encontrado"),
            @ApiResponse(responseCode = "404", description = "Pagamento não encontrado")
    })
    ResponseEntity<PagamentoResponseDTO> buscarPagamentoPorId(
            @Parameter(description = "ID do pagamento a ser buscado", required = true)
            Long id
    );

    @Operation(
            summary = "Lista todos os pagamentos",
            description = "Retorna uma lista contendo todos os pagamentos cadastrados no sistema."
    )
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    List<PagamentoResponseDTO> listarPagamento();

    @Operation(
            summary = "Cadastra um novo pagamento",
            description = "Insere um novo pagamento com base nas informações fornecidas no corpo da requisição."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pagamento inserido com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
    })
    ResponseEntity<String> inserirPagamento(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados do novo pagamento",
                    required = true,
                    content = @Content(schema = @Schema(implementation = PagamentoRequestDTO.class))
            )
            PagamentoRequestDTO dto
    );

    @Operation(
            summary = "Exclui um pagamento",
            description = "Remove permanentemente um pagamento com base no ID informado."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pagamento excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pagamento não encontrado")
    })
    ResponseEntity<String> excluirPagamento(
            @Parameter(description = "ID do pagamento a ser excluído", required = true)
            Long id
    );

    @Operation(
            summary = "Atualiza parcialmente o pagamento",
            description = "Atualiza um ou mais campos de um pagamento existente com base no ID informado."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pagamento atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pagamento não encontrado")
    })
    ResponseEntity<String> atualizarPagamento(
            @Parameter(description = "ID do pagamento a ser atualizado", required = true)
            Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Campos do pagamento a serem atualizados",
                    required = true,
                    content = @Content(schema = @Schema(implementation = PagamentoRequestDTO.class))
            )
            PagamentoRequestDTO dto
    );
}
