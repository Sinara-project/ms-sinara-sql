package org.example.sinara.open_api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.example.sinara.dto.request.RegistroPontoRequestDTO;
import org.example.sinara.dto.response.RegistroPontoResponseDTO;
import org.example.sinara.model.RegistroPonto;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;

public interface RegistroPontoOpenApi {

    @Operation(
            summary = "Lista registro de ponto por ID",
            description = "Retorna um objeto contendo todas as informações do registro de ponto pelo seu ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Registro de ponto encontrado"),
            @ApiResponse(responseCode = "404", description = "Registro de ponto não encontrado")
    })
    ResponseEntity<RegistroPontoResponseDTO> buscarRegistroPontoPorId(
            @Parameter(description = "ID do registro de ponto a ser buscado", required = true)
            Long id
    );

    @Operation(
            summary = "Lista todos os registros de ponto",
            description = "Retorna uma lista contendo todos os registros de ponto cadastrados no sistema."
    )
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    List<RegistroPontoResponseDTO> listarRegistroPonto();

    @Operation(
            summary = "Cadastra um novo registro de ponto",
            description = "Insere um novo registro de ponto com base nas informações fornecidas no corpo da requisição."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Registro de ponto inserido com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
    })
    ResponseEntity<String> inserirRegistroPonto(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados do novo registro de ponto",
                    required = true,
                    content = @Content(schema = @Schema(implementation = RegistroPontoRequestDTO.class))
            )
            RegistroPontoRequestDTO dto
    );

    @Operation(
            summary = "Exclui um registro de ponto",
            description = "Remove permanentemente um registro de ponto com base no ID informado."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Registro de ponto excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Registro de ponto não encontrado")
    })
    ResponseEntity<String> excluirRegistroPonto(
            @Parameter(description = "ID do registro de ponto a ser excluído", required = true)
            Long id
    );

    @Operation(
            summary = "Atualiza parcialmente o registro de ponto",
            description = "Atualiza um ou mais campos de um registro de ponto existente com base no ID informado."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Registro de ponto atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Registro de ponto não encontrado")
    })
    ResponseEntity<String> atualizarRegistroPonto(
            @Parameter(description = "ID do registro de ponto a ser atualizado", required = true)
            Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Campos do registro de ponto a serem atualizados",
                    required = true,
                    content = @Content(schema = @Schema(implementation = RegistroPontoRequestDTO.class))
            )
            RegistroPontoRequestDTO dto
    );

    @Operation(
            summary = "Lista todos os registros de ponto pela hora de entrada",
            description = "Retorna um objeto contendo todos os registros de ponto cadastrados com a hora de entrada especificada."
    )
    @ApiResponse(responseCode = "200", description = "Objeto retornado com sucesso")
    RegistroPonto buscarPorHorarioEntrada(
            @Parameter(description = "Hora de entrada do registro de ponto", required = true)
            LocalDateTime horarioEntrada
    );

    @Operation(
            summary = "Lista todos os registros de ponto pela hora de saída",
            description = "Retorna um objeto contendo todos os registros de ponto cadastrados com a hora de saída especificada."
    )
    @ApiResponse(responseCode = "200", description = "Objeto retornado com sucesso")
    RegistroPonto buscarPorHorarioSaida(
            @Parameter(description = "Hora de saída do registro de ponto", required = true)
            LocalDateTime horarioSaida
    );

}
