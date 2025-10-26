//package org.example.sinara.security;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.security.web.access.AccessDeniedHandler;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//import java.nio.file.AccessDeniedException;
//import java.util.Map;
//
//@Component
//public class CustomAccessDeniedHandler implements AccessDeniedHandler {
//    private final ObjectMapper mapper = new ObjectMapper();
//
//    @Override
//    public void handle(HttpServletRequest request, HttpServletResponse response, org.springframework.security.access.AccessDeniedException accessDeniedException) throws IOException, ServletException {
//        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
//        response.setContentType("application/json");
//
//        Map<String, String> errorResponse = Map.of(
//                "error", "Você não tem permissão para acessar esse recurso"
//        );
//
//        response.getWriter().write(mapper.writeValueAsString(errorResponse));
//    }
//}
//
//
