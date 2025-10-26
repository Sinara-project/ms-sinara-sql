//package org.example.sinara.security;
//
//import org.example.sinara.model.User;
//import org.example.sinara.repository.sql.UserRepository;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import java.util.Set;
//import java.util.stream.Collectors;
//
//@Service
//public class CustomDetailsService implements UserDetailsService {
//    private final UserRepository userRepository;
//
//    public CustomDetailsService(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
//
//    @Override
//    public UserDetails loadUserByUsername(String nome) throws UsernameNotFoundException {
//        User user = userRepository.findByNomeIgnoreCase(nome)
//                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + nome));
//
//        Set<GrantedAuthority> grantedAuthorities = user.getRoles().stream()
//                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getNome()))
//                .collect(Collectors.toSet());
//
//        return new org.springframework.security.core.userdetails.User(
//                user.getNome(),
//                user.getSenha(),
//                grantedAuthorities
//        );
//    }
//
//}
