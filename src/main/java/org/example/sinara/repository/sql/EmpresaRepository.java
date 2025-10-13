package org.example.sinara.repository.sql;

import org.example.sinara.model.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

public interface EmpresaRepository extends JpaRepository<Empresa, Long> {

//    Métodos derivados

    Empresa findByImagemUrl (String imagemUrl);

    Empresa findByCodigo (String codigo);

    Empresa findByCnpj (String cnpj);

    Empresa findByNome(String nome);

    Empresa findByEmail (String email);

    Empresa findByRamoAtuacaoLikeIgnoreCase (String ramoAtuacao);

    @Procedure(name = "mudar_para_premium")
    void mudarParaPremium(@Param("p_id_empresa") Integer empresaId,
                          @Param("p_id_cartao") Integer cartaoId);

}
