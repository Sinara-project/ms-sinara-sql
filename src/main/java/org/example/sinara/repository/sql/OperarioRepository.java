package org.example.sinara.repository.sql;

import org.example.sinara.model.Operario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperarioRepository extends JpaRepository<Operario, Long> {
    Operario findByNome(String nome);

    Operario findByHorasPrevistas(int horasPrevistas);
}
