package org.example.sinara.open_api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.example.sinara.dto.request.CartaoCreditoRequestDTO;
import org.example.sinara.dto.response.CartaoCreditoResponseDTO;
import org.example.sinara.dto.response.EmpresaResponseDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CartaoCreditoOpenApi {

    @Operation(
            summary = "Lista cartão de crédito por id por ID",
            description = "Retorna um produto contendo todas as informações do cartão de crédito pelo seu ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cartão de crédito encontrado"),
            @ApiResponse(responseCode = "404", description = "Cartão de crédito não encontrado")
    })
    ResponseEntity<CartaoCreditoResponseDTO> buscarCartaoCreditoPorId(
            @Parameter(description = "ID do Cartão de crédito a ser buscada", required = true)
            Integer id
    );

    @Operation(
            summary = "Lista todos os cartões de crédito",
            description = "Retorna uma lista contendo todos os cartões de crédito cadastrados no sistema."
    )
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    List<CartaoCreditoResponseDTO> listarCartaoCredito();

    @Operation(
            summary = "Cadastra um novo cartão de crédito",
            description = "Insere um novo cartão de crédito com base nas informações fornecidas no corpo da requisição."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cartão de crédito inserido com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
    })
    ResponseEntity<String> inserirCartaoCredito(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados do novo cartão de crédito",
                    required = true,
                    content = @Content(schema = @Schema(implementation = CartaoCreditoRequestDTO.class))
            )
            CartaoCreditoRequestDTO dto
    );

    @Operation(
            summary = "Exclui um cartão de crédito",
            description = "Remove permanentemente um cartão de crédito com base no ID informado."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cartão de crédito excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Cartão de crédito não encontrado")
    })
    ResponseEntity<String> excluirCartaoCredito(
            @Parameter(description = "ID do cartão de crédito a ser excluído", required = true)
            Integer id
    );

    @Operation(
            summary = "Atualiza parcialmente um cartão de crédito",
            description = "Atualiza um ou mais campos de um cartão de crédito existente com base no ID informado."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cartão de crédito atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Cartão de crédito não encontrado")
    })
    ResponseEntity<String> atualizarProdutoParcial(
            @Parameter(description = "ID do cartão de crédito a ser atualizado", required = true)
            Integer id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Campos do cartão de crédito a serem atualizados",
                    required = true,
                    content = @Content(schema = @Schema(implementation = CartaoCreditoRequestDTO.class))
            )
            CartaoCreditoRequestDTO dto
    );

    @Operation(
            summary = "Valida um cartão de crédito",
            description = "Verifica se o cartão de crédito informado é válido com base no número e na data de validade."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Retorno indicando se o cartão é válido ou não"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos fornecidos")
    })
    ResponseEntity<Boolean> validarCartao(
            @Parameter(description = "Número do cartão de crédito", required = true)
            String numero,
            @Parameter(description = "Data de validade do cartão no formato ISO (yyyy-MM-dd)", required = true)
            java.time.LocalDate validade
    );

}
