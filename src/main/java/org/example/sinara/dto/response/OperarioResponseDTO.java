package org.example.sinara.dto.response;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
public class OperarioResponseDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer idEmpresa;

    private String url;

    private String imagemUrl;

    private String cpf;

    private String nome;

    private String email;

    private String cargo;

    private String setor;

    private Boolean ferias;

    private Boolean ativo;

    private String senha;

    private Integer horasPrevistas;

}
