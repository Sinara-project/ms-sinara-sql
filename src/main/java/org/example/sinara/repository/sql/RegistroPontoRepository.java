package org.example.sinara.repository.sql;

import org.example.sinara.model.Empresa;
import org.example.sinara.model.RegistroPonto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface RegistroPontoRepository extends JpaRepository<RegistroPonto, Long> {

//    Métodos derivados
    RegistroPonto findByHorarioEntrada (LocalDateTime horarioEntrada);

    RegistroPonto findByHorarioSaida (LocalDateTime horarioSaida);
}
