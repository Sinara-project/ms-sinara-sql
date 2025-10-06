package org.example.sinara.open_api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.example.sinara.dto.request.CartaoCreditoRequestDTO;
import org.example.sinara.dto.response.CartaoCreditoResponseDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CartaoCreditoOpenApi {

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
            Long id
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
            Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Campos do cartão de crédito a serem atualizados",
                    required = true,
                    content = @Content(schema = @Schema(implementation = CartaoCreditoRequestDTO.class))
            )
            CartaoCreditoRequestDTO dto
    );
}
