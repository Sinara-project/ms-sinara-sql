package org.example.sinara.repository.sql;

import org.example.sinara.model.AcessoDiarioUsuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AcessoDiarioUsuariosRepository extends JpaRepository<AcessoDiarioUsuarios, Integer> {
}