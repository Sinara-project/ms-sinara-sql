package org.example.sinara.repository.sql;

import org.example.sinara.model.Permissoes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissoesRepository extends JpaRepository<Permissoes, Long> {
}
