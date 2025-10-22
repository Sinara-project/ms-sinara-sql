package org.example.sinara.open_api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.example.sinara.dto.SenhaRequestDTO;
import org.example.sinara.dto.request.EmpresaRequestDTO;
import org.example.sinara.dto.response.EmpresaResponseDTO;
import org.example.sinara.model.Empresa;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface EmpresaOpenApi {

    @Operation(
            summary = "Lista empresa por ID",
            description = "Retorna um produto contendo todas as informações da empresa pelo seu ID."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Empresa encontrada"),
        @ApiResponse(responseCode = "404", description = "Empresa não encontrada")
    })
    ResponseEntity<EmpresaResponseDTO> buscarEmpresaPorId(
            @Parameter(description = "ID da empresa a ser buscada", required = true)
            Integer id
    );

    @Operation(
            summary = "Lista todos as empresas",
            description = "Retorna uma lista contendo todas as empresas cadastradas no sistema."
    )
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    List<EmpresaResponseDTO> listarEmpresas();

    @Operation(
            summary = "Cadastra uma nova empresa",
            description = "Insere uma nova empresa com base nas informações fornecidas no corpo da requisição."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Empresa inserida com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
    })
    ResponseEntity<String> inserirEmpresa(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados da nova empresa",
                    required = true,
                    content = @Content(schema = @Schema(implementation = EmpresaRequestDTO.class))
            )
            EmpresaRequestDTO dto
    );

    @Operation(
            summary = "Exclui uma empresa",
            description = "Remove permanentemente uma empresa com base no ID informado."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Empresa excluída com sucesso"),
            @ApiResponse(responseCode = "404", description = "Empresa não encontrada")
    })
    ResponseEntity<String> excluirEmpresa(
            @Parameter(description = "ID da empresa a ser excluída", required = true)
            Integer id
    );

    @Operation(
            summary = "Atualiza parcialmente a empresa",
            description = "Atualiza um ou mais campos de uma empresa existente com base no ID informado."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Empresa atualizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Empresa não encontrada")
    })
    ResponseEntity<String> atualizarEmpresa(
            @Parameter(description = "ID da empresa a ser atualizada", required = true)
            Integer id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Campos da empresa a serem atualizados",
                    required = true,
                    content = @Content(schema = @Schema(implementation = EmpresaRequestDTO.class))
            )
           EmpresaRequestDTO dto
    );

    @Operation(
            summary = "Busca o perfil da empresa",
            description = "Retorna todas as informações necessárias para a tela de perfil de uma empresa, com base no ID informado."
    )
    @ApiResponse(responseCode = "200", description = "Informações retornadas com sucesso")
    ResponseEntity<Map<String, Object>> buscarPerfil(
            @Parameter(description = "ID da empresa", required = true)
            Integer id
    );

    @Operation(
            summary = "Busca id de empresa pelo cnpj",
            description = "Retorna ID da empresa, com base no CNPJ informado"
    )
    @ApiResponse(responseCode = "200", description = "Informações retornadas com sucesso")
    String obterId(
            @Parameter(description = "CNPJ da empresa", required = true)
            String cnpj
    );

    @Operation(
            summary = "Atualiza a senha da área restrita",
            description = "Atualiza a senha de acesso à área restrita de uma empresa com base no ID informado."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Senha atualizada com sucesso",
                    content = @Content(schema = @Schema(implementation = EmpresaResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Empresa não encontrada"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
    })
    ResponseEntity<EmpresaResponseDTO> atualizarSenhaAreaRestrita(
            @Parameter(description = "ID da empresa para atualizar a senha", required = true)
            Integer id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Nova senha a ser atualizada",
                    required = true,
                    content = @Content(schema = @Schema(implementation = SenhaRequestDTO.class))
            )
            SenhaRequestDTO request
    );
}
