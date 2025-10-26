//package org.example.sinara.security;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.NoOpPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//public class SecurityConfig {
//    private final CustomAccessDeniedHandler customAccessDeniedHandler;
//    private final CustomDetailsService customDetailsService;
//
//    public SecurityConfig(CustomAccessDeniedHandler customAccessDeniedHandler,
//                          CustomDetailsService customDetailsService) {
//        this.customAccessDeniedHandler = customAccessDeniedHandler;
//        this.customDetailsService = customDetailsService;
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
//                        .requestMatchers("/api/user/**").hasRole("USER")
//                        .anyRequest().authenticated()
//                )
//                .exceptionHandling(ex -> ex.accessDeniedHandler(customAccessDeniedHandler))
//                .formLogin(Customizer.withDefaults())
//                .userDetailsService(customDetailsService);
//
//        return http.build();
//    }
//}
//
