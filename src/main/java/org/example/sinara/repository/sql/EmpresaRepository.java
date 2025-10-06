package org.example.sinara.repository.sql;

import org.example.sinara.model.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpresaRepository extends JpaRepository<Empresa, Long> {

//    Métodos derivados

    Empresa findByImagemUrl (String imagemUrl);

    Empresa findByCodigo (String codigo);

    Empresa findByCnpj (String cnpj);

    Empresa findByNome(String nome);

    Empresa findByEmail (String email);

    Empresa findByRamoAtuacaoLikeIgnoreCase (String ramoAtuacao);
}
