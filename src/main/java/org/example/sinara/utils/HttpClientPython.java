package org.example.sinara.utils;

import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import java.util.Map;

@Component
public class HttpClientPython {

    private final String baseUrl = "http://localhost:8000"; // Flask

    // envia imagem inicial e recebe URL do Cloudinary
    public String uploadImagem(MultipartFile file) {
        try {
            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("image", new MultipartInputStreamFileResource(file.getInputStream(), file.getOriginalFilename()));

            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

            ResponseEntity<Map> response = restTemplate.exchange(
                    baseUrl + "/upload_image",
                    HttpMethod.POST,
                    requestEntity,
                    Map.class
            );

            Map<String, Object> responseBody = response.getBody();
            if (responseBody != null && responseBody.containsKey("url")) {
                return responseBody.get("url").toString();
            }
            throw new RuntimeException("Erro ao enviar imagem: resposta inválida do Flask.");
        } catch (Exception e) {
            throw new RuntimeException("Erro ao enviar imagem ao Flask: " + e.getMessage(), e);
        }
    }

    // 🔹 Envia imagem de verificação
    public boolean chamarVerificacaoFacial(Integer idOperario, String caminhoImagem) {
        try {
            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("user_id", idOperario.toString());
            body.add("foto_teste", new MultipartInputStreamFileResource(
                    new java.io.FileInputStream(caminhoImagem), "foto_teste.jpg"
            ));

            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

            ResponseEntity<Map> response = restTemplate.exchange(
                    baseUrl + "/verificar_face",
                    HttpMethod.POST,
                    requestEntity,
                    Map.class
            );

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
