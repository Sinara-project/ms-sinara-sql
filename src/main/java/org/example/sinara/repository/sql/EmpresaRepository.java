package org.example.sinara.repository.sql;

import org.example.sinara.model.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpresaRepository extends JpaRepository<Empresa, Long> {

//    Métodos derivados

    Empresa findByCnpj (String cnpj);

    Empresa findByNome(String nome);

    Empresa findByEmailCorporativo (String emailCorporativo);

    Empresa findByRamoAtuacaoLikeIgnoreCase (String ramoAtuacao);
}
