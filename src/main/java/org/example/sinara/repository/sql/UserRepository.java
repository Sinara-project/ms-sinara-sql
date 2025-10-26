package org.example.sinara.repository.sql;

import org.example.sinara.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByNomeIgnoreCase(String nome);
}