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
import java.util.Map;

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
            Integer id
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
            Integer id
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
            Integer id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Campos do registro de ponto a serem atualizados",
                    required = true,
                    content = @Content(schema = @Schema(implementation = RegistroPontoRequestDTO.class))
            )
            RegistroPontoRequestDTO dto
    );

    @Operation(
            summary = "Busca um registro de ponto pelo ID",
            description = "Retorna o registro de ponto correspondente ao ID informado, incluindo hora de entrada e saída."
    )
    @ApiResponse(responseCode = "200", description = "Registro retornado com sucesso")
    Map<String, String> buscarHorarios(
            @Parameter(description = "Hora de entrada e saída do registro de ponto", required = true)
            Integer id
    );

    @Operation(
            summary = "Verifica o status do operário",
            description = "Retorna um boolean indicando se o operário está ativo ou inativo."
    )
    @ApiResponse(responseCode = "200", description = "Status retornado com sucesso")
    ResponseEntity<Boolean> listarStatusOperario(
            @Parameter(description = "ID do operário", required = true)
            Integer idOperario
    );

    @Operation(
            summary = "Busca o último turno do operário",
            description = "Retorna uma string com o horário do último turno registrado do operário."
    )
    @ApiResponse(responseCode = "200", description = "Último turno retornado com sucesso")
    ResponseEntity<String> getUltimoTurno(
            @Parameter(description = "ID do operário", required = true)
            Integer idOperario
    );

    @Operation(
            summary = "Calcula a quantidade de pontos de registro do operário",
            description = "Retorna o total de registros de ponto feitos pelo operário."
    )
    @ApiResponse(responseCode = "200", description = "Quantidade de pontos retornada com sucesso")
    ResponseEntity<Integer> calcularPontosRegistrados(
            @Parameter(description = "ID do operário", required = true)
            Integer idOperario
    );

    @Operation(
            summary = "Calcula as horas trabalhadas no mês",
            description = "Retorna o total de horas trabalhadas pelo operário no mês atual."
    )
    @ApiResponse(responseCode = "200", description = "Horas trabalhadas retornadas com sucesso")
    ResponseEntity<Map<String, String>> calcularHorasTrabalhadas(
            @Parameter(description = "ID do operário", required = true)
            Integer idOperario
    );

    @Operation(
            summary = "Calcula o banco de horas do operário",
            description = "Retorna o total de horas extras acumuladas pelo operário no banco de horas."
    )
    @ApiResponse(responseCode = "200", description = "Banco de horas retornado com sucesso")
    ResponseEntity<Map<String, String>> getBancoHoras(
            @Parameter(description = "ID do operário", required = true)
            Integer idOperario
    );

}
