package org.example.sinara.dto.request;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PermissoesRequestDTO {
    @NotNull(message = "Permissão obrigatória")
    private Boolean adicionarUsuario = false;

    @NotNull(message = "Permissão obrigatória")
    private Boolean responderFormulario = false;

    @NotNull(message = "Permissão obrigatória")
    private Boolean visualizarDashboards = false;

    @NotNull(message = "Permissão obrigatória")
    private Boolean criarFormulario = false;

    @NotNull(message = "Permissão obrigatória")
    private Boolean visualizarFormulario = false;

    @NotNull(message = "Permissão obrigatória")
    private Boolean editarDadoOperario = false;

    @Positive(message = "O ID do operário deve ser positivo")
    @NotNull(message = "O ID do operário é obrigatório")
    private Long idOperario;
}
