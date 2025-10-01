package org.example.sinara.dto.response;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PermissoesResponseDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean adicionarUsuario;

    private Boolean responderFormulario;

    private Boolean visualizarDashboards;

    private Boolean criarFormulario;

    private Boolean visualizarFormulario;

    private Boolean editarDadoOperario;

    private Long idOperario;
}
