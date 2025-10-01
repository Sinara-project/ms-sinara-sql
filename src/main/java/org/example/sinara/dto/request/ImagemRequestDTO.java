package org.example.sinara.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.example.sinara.validation.OnCreate;

@Getter
@Setter
public class ImagemRequestDTO {

    @Positive(message = "O ID do operário deve ser positivo")
    @NotNull(message = "O ID do operário é obrigatório")
    private Integer idOperario;

    @NotBlank(message = "A url não pode estar em branco", groups = OnCreate.class)
    @Size(max = 255, message = "URL deve ter no máximo 255 caracteres")
    private String URL;
}
