package org.example.sinara.open_api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.example.sinara.dto.request.PlanosRequestDTO;
import org.example.sinara.dto.response.PlanosResponseDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PlanosOpenApi {

    @Operation(
            summary = "Lista plano por ID",
            description = "Retorna um objeto contendo todas as informações do plano pelo seu ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Plano encontrado"),
            @ApiResponse(responseCode = "404", description = "Plano não encontrado")
    })
    ResponseEntity<PlanosResponseDTO> buscarPlanosPorId(
            @Parameter(description = "ID do plano a ser buscado", required = true)
            Long id
    );

    @Operation(
            summary = "Lista todos os planos",
            description = "Retorna uma lista contendo todos os planos cadastrados no sistema."
    )
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    List<PlanosResponseDTO> listarPlanos();

    @Operation(
            summary = "Cadastra um novo plano",
            description = "Insere um novo plano com base nas informações fornecidas no corpo da requisição."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Plano inserido com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
    })
    ResponseEntity<String> inserirPlanos(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados do novo plano",
                    required = true,
                    content = @Content(schema = @Schema(implementation = PlanosRequestDTO.class))
            )
            PlanosRequestDTO dto
    );

    @Operation(
            summary = "Exclui um plano",
            description = "Remove permanentemente um plano com base no ID informado."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Plano excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Plano não encontrado")
    })
    ResponseEntity<String> excluirPlanos(
            @Parameter(description = "ID do plano a ser excluído", required = true)
            Long id
    );

    @Operation(
            summary = "Atualiza parcialmente o plano",
            description = "Atualiza um ou mais campos de um plano existente com base no ID informado."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Plano atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Plano não encontrado")
    })
    ResponseEntity<String> atualizarPlanos(
            @Parameter(description = "ID do plano a ser atualizado", required = true)
            Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Campos do plano a serem atualizados",
                    required = true,
                    content = @Content(schema = @Schema(implementation = PlanosRequestDTO.class))
            )
            PlanosRequestDTO dto
    );
}
