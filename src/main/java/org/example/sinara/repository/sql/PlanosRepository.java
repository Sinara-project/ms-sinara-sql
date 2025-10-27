package org.example.sinara.repository.sql;

import org.example.sinara.model.Planos;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanosRepository extends JpaRepository<Planos, Integer> {
    boolean existsByNome(String nome);
}
