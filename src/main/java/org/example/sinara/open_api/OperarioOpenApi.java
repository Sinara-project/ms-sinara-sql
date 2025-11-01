package org.example.sinara.open_api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.example.sinara.dto.request.OperarioRequestDTO;
import org.example.sinara.dto.response.OperarioResponseDTO;
import org.example.sinara.model.Operario;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface OperarioOpenApi {
    @Operation(
            summary = "Lista operário por ID",
            description = "Retorna um produto contendo todas as informações do operário pelo seu ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Operário encontrado"),
            @ApiResponse(responseCode = "404", description = "Operário não encontrado")
    })
    ResponseEntity<OperarioResponseDTO> buscarOperarioPorId(
            @Parameter(description = "ID do operário a ser buscado", required = true)
            Integer id
    );

    @Operation(
            summary = "Lista todos os operários",
            description = "Retorna uma lista contendo todos os operários cadastrados no sistema."
    )
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    List<OperarioResponseDTO> listarOperario();

    @Operation(
            summary = "Cadastra um novo operário",
            description = "Insere um novo operário com base nas informações fornecidas no corpo da requisição."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Operário inserido com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
    })
    ResponseEntity<String> inserirOperario(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados do novo operário",
                    required = true,
                    content = @Content(schema = @Schema(implementation = OperarioRequestDTO.class))
            )
            OperarioRequestDTO dto
    );

    @Operation(
            summary = "Exclui um operário",
            description = "Remove permanentemente um operário com base no ID informado."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Operário excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Operário não encontrado")
    })
    ResponseEntity<String> excluirOperario(
            @Parameter(description = "ID do operário a ser excluído", required = true)
            Integer id
    );

    @Operation(
            summary = "Atualiza parcialmente o operário",
            description = "Atualiza um ou mais campos de um operário existente com base no ID informado."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Operário atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Operário não encontrado")
    })
    ResponseEntity<String> atualizarOperario(
            @Parameter(description = "ID do operário a ser atualizado", required = true)
            Integer id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Campos do operário a serem atualizados",
                    required = true,
                    content = @Content(schema = @Schema(implementation = OperarioRequestDTO.class))
            )
            OperarioRequestDTO dto
    );

    @Operation(
            summary = "Busca o perfil do operário",
            description = "Retorna todas as informações necessárias para a tela de perfil de um operário, com base no ID informado."
    )
    @ApiResponse(responseCode = "200", description = "Informações retornadas com sucesso")
    ResponseEntity<Map<String, Object>> buscarPerfil(
            @Parameter(description = "ID do operário", required = true)
            Integer id
    );

    @Operation(
            summary = "Lista todos os operários de uma empresa",
            description = "Retorna todos os operários vinculados a uma empresa específica, com base no ID da empresa informado."
    )
    @ApiResponse(responseCode = "200", description = "Lista de operários retornada com sucesso")
    ResponseEntity<List<Map<String, Object>>> listarOperariosPorEmpresa(
            @Parameter(description = "ID da empresa", required = true)
            Integer idEmpresa
    );

    @Operation(
            summary = "Atualiza o status do operário",
            description = "Atualiza os campos de status de um operário (ativo e férias) com base no ID informado."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Status do operário atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Operário não encontrado")
    })
    String atualizarStatus(
            @Parameter(description = "ID do operário a ser atualizado", required = true)
            Integer idOperario,
            @Parameter(description = "Define se o operário está ativo", required = true)
            Boolean ativo,
            @Parameter(description = "Define se o operário está de férias", required = true)
            Boolean ferias
    );

    @Operation(
            summary = "Obtém o ID do operário pelo CPF",
            description = "Retorna o ID de um operário com base no CPF informado na URL."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "ID encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Operário não encontrado para o CPF informado")
    })
    String obterId(
            @Parameter(description = "CPF do operário a ser buscado", required = true)
            String cpf
    );

    @Operation(
            summary = "Realiza o login do operário",
            description = "Valida as credenciais informadas (email, CPF, senha e código da empresa) e retorna true se o login for válido, ou false caso contrário."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Login verificado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
    })
    ResponseEntity<Boolean> loginOperario(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados de login do operário",
                    required = true,
                    content = @Content(schema = @Schema(implementation = org.example.sinara.dto.request.OperarioLoginRequestDTO.class))
            )
            org.example.sinara.dto.request.OperarioLoginRequestDTO loginDTO
    );

    @Operation(
            summary = "Envia imagem para reconhecimento facial do operário",
            description = "Recebe uma imagem (foto) enviada pelo cliente e associa ao operário identificado pelo ID informado. "
                    + "A imagem será utilizada posteriormente para autenticação facial do operário."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Imagem de reconhecimento facial salva com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida ou formato de arquivo incorreto"),
            @ApiResponse(responseCode = "404", description = "Operário não encontrado para o ID informado")
    })
    ResponseEntity<String> uploadFotoReconhecimento(
            @Parameter(description = "ID do operário para associar a imagem", required = true)
            Integer id,
            @Parameter(
                    description = "Arquivo de imagem contendo o rosto do operário (formato .jpg, .jpeg ou .png)",
                    required = true,
                    content = @Content(mediaType = "multipart/form-data")
            )
            org.springframework.web.multipart.MultipartFile file
    );


    @Operation(
            summary = "Verifica o reconhecimento facial do operário",
            description = "Compara uma imagem enviada pelo cliente com a imagem de referência armazenada para o operário informado. "
                    + "Retorna **true** se o rosto for reconhecido com sucesso, ou **false** caso contrário."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Verificação facial realizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida ou formato de imagem incorreto"),
            @ApiResponse(responseCode = "404", description = "Operário não encontrado ou sem imagem cadastrada")
    })
    ResponseEntity<Boolean> verificarFacial(
            @Parameter(description = "ID do operário a ser verificado", required = true)
            Integer userId,
            @Parameter(
                    description = "Imagem capturada para verificação facial (formato .jpg, .jpeg ou .png)",
                    required = true,
                    content = @Content(mediaType = "multipart/form-data")
            )
            org.springframework.web.multipart.MultipartFile fotoTeste
    );
}
