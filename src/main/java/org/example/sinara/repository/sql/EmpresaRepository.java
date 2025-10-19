package org.example.sinara.repository.sql;

import org.example.sinara.model.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.util.Map;

public interface EmpresaRepository extends JpaRepository<Empresa, Long> {

//    Query
    @Query(value = """
        SELECT 
            e.id AS id,
            e.imagem_url AS imagemUrl,
            e.codigo AS codigo,
            e.cnpj AS cnpj,
            e.nome AS nome,
            e.email AS email,
            e.ramo_atuacao AS ramoAtuacao,
            e.telefone AS telefone,
            e.plano_inicial AS planoInicial
        FROM empresa e
        WHERE e.id = :id
    """, nativeQuery = true)
    Map<String, Object> buscarPerfilPorId(@Param("id") Long id);

//    Métodos derivados

//    Empresa findByImagemUrl (String imagemUrl);
//
//    Empresa findByCodigo (String codigo);
//
//    Empresa findByCnpj (String cnpj);
//
//    Empresa findByNome(String nome);
//
//    Empresa findByEmail (String email);
//
//    Empresa findByRamoAtuacao (Long id);
//
    boolean existsByCodigo(String codigo);

    //Procedure
    @Procedure(name = "mudar_para_premium")
    void mudarParaPremium(@Param("p_id_empresa") Integer empresaId,
                          @Param("p_id_cartao") Integer cartaoId);

}
