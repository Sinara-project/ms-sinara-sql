package org.example.sinara.utils;

import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

@Component
public class ReconhecimentoFacial {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String baseUrl = "http://3.224.165.233:8000/face/verificar_face";

    public boolean verificarFace(Integer userId, MultipartFile fotoTeste) {
        try {
            // Corpo da requisição form-data
            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("user_id", userId.toString());
            body.add("foto_teste",
                    new MultipartInputStreamFileResource(fotoTeste.getInputStream(), fotoTeste.getOriginalFilename()));

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

            ResponseEntity<String> response = restTemplate.postForEntity(baseUrl, requestEntity, String.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                String bodyResp = response.getBody();
                return bodyResp != null && bodyResp.contains("\"resultado\": true");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
