package org.example.sinara.open_api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.example.sinara.dto.request.SenhaRequestDTO;
import org.example.sinara.dto.request.EmpresaRequestDTO;
import org.example.sinara.dto.response.EmpresaResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

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
    ResponseEntity<?> inserirEmpresa(
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
    ResponseEntity<Map<String, Object>> obterIdECodigo(
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

    @Operation(
            summary = "Rebaixa planos de empresas inadimplentes",
            description = "Rebaixa os planos das empresas que estão inadimplentes."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Planos rebaixados com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro ao tentar rebaixar os planos")
    })
    ResponseEntity<String> rebaixarPlanos();

    @Operation(
            summary = "Atualiza o plano de uma empresa para Premium",
            description = "Chama a procedure para atualizar o plano de uma empresa para Premium e registrar o pagamento."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Plano atualizado para Premium com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro ao atualizar o plano")
    })
    String mudarParaPremium(
            @Parameter(description = "ID da empresa que terá o plano atualizado", required = true)
            @RequestParam Integer idEmpresa,
            @Parameter(description = "ID do cartão usado para pagamento", required = true)
            @RequestParam Integer idCartao
    );

    @Operation(
            summary = "Realiza o login da área restrita da empresa",
            description = "Valida as credenciais de acesso à área restrita da empresa com base no ID e na senha informados. "
                    + "Retorna **true** se as credenciais forem válidas e **false** caso contrário."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Login verificado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "401", description = "Credenciais incorretas"),
            @ApiResponse(responseCode = "404", description = "Empresa não encontrada")
    })
    ResponseEntity<Boolean> loginAreaRestrita(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados de login da área restrita (ID da empresa e senha)",
                    required = true,
                    content = @Content(schema = @Schema(implementation = org.example.sinara.dto.request.LoginRestritoRequestDTO.class))
            )
            org.example.sinara.dto.request.LoginRestritoRequestDTO dto
    );

    @Operation(
            summary = "Realiza o login da empresa no sistema",
            description = "Autentica a empresa com base no **CNPJ** e **senha** informados. "
                    + "Retorna as informações da empresa em caso de sucesso ou uma mensagem de erro caso as credenciais sejam inválidas."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Login realizado com sucesso",
                    content = @Content(schema = @Schema(implementation = org.example.sinara.dto.response.EmpresaLoginResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Empresa não encontrada ou senha incorreta")
    })
    ResponseEntity<?> login(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados de login da empresa (CNPJ e senha)",
                    required = true,
                    content = @Content(schema = @Schema(implementation = org.example.sinara.dto.request.EmpresaLoginRequestDTO.class))
            )
            org.example.sinara.dto.request.EmpresaLoginRequestDTO loginDTO
    );


    @Operation(
            summary = "Atualiza a senha de uma empresa",
            description = "Atualiza a senha de acesso de uma empresa com base no ID informado. "
                    + "Este endpoint deve ser utilizado para redefinir a senha de login da empresa no sistema."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Senha atualizada com sucesso",
                    content = @Content(schema = @Schema(implementation = org.example.sinara.dto.response.EmpresaResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Requisição inválida ou senha fora dos padrões permitidos"),
            @ApiResponse(responseCode = "404", description = "Empresa não encontrada")
    })
    ResponseEntity<org.example.sinara.dto.response.EmpresaResponseDTO> atualizarSenha(
            @Parameter(description = "ID da empresa que terá a senha atualizada", required = true)
            Integer id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Nova senha a ser configurada para a empresa",
                    required = true,
                    content = @Content(schema = @Schema(implementation = org.example.sinara.dto.request.SenhaRequestDTO.class))
            )
            org.example.sinara.dto.request.SenhaRequestDTO request
    );
}
