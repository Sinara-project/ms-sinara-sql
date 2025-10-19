package org.example.sinara.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class OperarioResponseDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long idEmpresa;

    private String url;

    private String imagemUrl;

    private String cpf;

    private String nome;

    private String email;

    private String cargo;

    private String setor;

    private Integer horasPrevistas;

    private Boolean ferias;

    private Boolean ativo;

    private String senha;
}
