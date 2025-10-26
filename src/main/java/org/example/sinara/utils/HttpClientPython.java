package org.example.sinara.utils;

import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.FileSystemResource;

import java.io.File;
import java.util.Map;

@Component
public class HttpClientPython {

    private final String baseUrl = "http://localhost:8000"; // Porta do Flask de reconhecimento facial

    // Envia imagem para o Flask e retorna se o rosto bateu (True/False)
    public boolean chamarVerificacaoFacial(Integer idOperario, String caminhoImagem) {
        try {
            RestTemplate restTemplate = new RestTemplate();

            // Envia como multipart/form-data
            File file = new File(caminhoImagem);
            if (!file.exists()) {
                throw new RuntimeException("Arquivo de imagem não encontrado: " + caminhoImagem);
            }

            FileSystemResource resource = new FileSystemResource(file);

            // Monta o corpo multipart
            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("user_id", idOperario.toString());
            body.add("foto_teste", resource);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

            ResponseEntity<Map> response = restTemplate.exchange(
                    baseUrl + "/verificar_face",
                    HttpMethod.POST,
                    requestEntity,
                    Map.class
            );

            // Espera um JSON tipo {"resultado": true}
            Map<String, Object> responseBody = response.getBody();
            if (responseBody != null && responseBody.containsKey("resultado")) {
                return (boolean) responseBody.get("resultado");
            }

            return false;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao chamar reconhecimento facial: " + e.getMessage(), e);
        }
    }
}
