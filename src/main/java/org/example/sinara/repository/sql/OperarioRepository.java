package org.example.sinara.repository.sql;

import org.example.sinara.model.Operario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

public interface OperarioRepository extends JpaRepository<Operario, Long> {
    Operario findByNome(String nome);

    Operario findByHorasPrevistas(int horasPrevistas);

    @Procedure(procedureName = "atualizar_status_funcionario")
    void atualizarStatus(@Param("p_id_operario") Integer operarioId,
                         @Param("p_ativo") Boolean ativo,
                         @Param("p_ferias") Boolean ferias);
}
