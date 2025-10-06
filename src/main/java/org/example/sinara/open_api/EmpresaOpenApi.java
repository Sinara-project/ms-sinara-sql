package org.example.sinara.open_api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.example.sinara.dto.request.EmpresaRequestDTO;
import org.example.sinara.dto.response.EmpresaResponseDTO;
import org.example.sinara.model.Empresa;
import org.springframework.http.ResponseEntity;

import java.util.List;

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
            Long id
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
            Long id
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
            Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Campos da empresa a serem atualizados",
                    required = true,
                    content = @Content(schema = @Schema(implementation = EmpresaRequestDTO.class))
            )
           EmpresaRequestDTO dto
    );

    @Operation(
            summary = "Lista todas as imagens das empresas",
            description = "Retorna um objeto contendo todas as imagens cadastradas nas empresas."
    )
    @ApiResponse(responseCode = "200", description = "Objeto retornado com sucesso")
    Empresa buscarPorImageUrl(
            @Parameter(description = "URL da imagem", required = true)
            String imageUrl
    );

    @Operation(
            summary = "Lista todas os códigos das empresas",
            description = "Retorna um objeto contendo todas os códigos cadastrados nas empresas."
    )
    @ApiResponse(responseCode = "200", description = "Objeto retornado com sucesso")
    Empresa buscarPorCodigo(
            @Parameter(description = "Código da empresa", required = true)
            String codigo
    );

    @Operation(
            summary = "Lista todos os cnpj das empresas",
            description = "Retorna um objeto contendo todos os cnpj cadastrados nas empresas."
    )
    @ApiResponse(responseCode = "200", description = "Objeto retornado com sucesso")
    Empresa buscarPorCnpj(
            @Parameter(description = "CNPJ da empresa", required = true)
            String cnpj
    );

    @Operation(
            summary = "Lista todas os nomes das empresas",
            description = "Retorna um objeto contendo todos os nomes cadastrados nas empresas."
    )
    @ApiResponse(responseCode = "200", description = "Objeto retornado com sucesso")
    Empresa buscarPorNome(
            @Parameter(description = "Nome da empresa", required = true)
            String nome
    );

    @Operation(
            summary = "Lista todos os e-mails das empresas",
            description = "Retorna um objeto contendo todos os e-mails cadastrados nas empresas."
    )
    @ApiResponse(responseCode = "200", description = "Objeto retornado com sucesso")
    Empresa buscarPorEmail(
            @Parameter(description = "E-mail da empresa", required = true)
            String email
    );

    @Operation(
            summary = "Lista ramo atuação das empresas",
            description = "Retorna um objeto contendo ramo atuação cadastrados nas empresas."
    )
    @ApiResponse(responseCode = "200", description = "Objeto retornado com sucesso")
    Empresa buscarPorRamoAtuacao(
            @Parameter(description = "Ramo de atuação da empresa", required = true)
            String ramoAtuacao
    );
}
