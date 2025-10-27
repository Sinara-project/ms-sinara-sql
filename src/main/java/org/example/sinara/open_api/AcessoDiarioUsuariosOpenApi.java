package org.example.sinara.open_api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.example.sinara.model.AcessoDiarioUsuarios;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

@Tag(name = "Acesso Diário de Usuários", description = "Endpoints para registrar atividades diárias dos operários")
public interface AcessoDiarioUsuariosOpenApi {

    @Operation(
            summary = "Registrar atividade diária",
            description = "Registra uma nova atividade no relatório diário chamando a procedure `registrar_daily_activity_report`."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Atividade registrada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @PostMapping("/registrar")
    String registrar(@RequestBody AcessoDiarioUsuarios request);
}
