package org.example.sinara.repository.sql;

import org.example.sinara.model.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PagamantoRepository extends JpaRepository<Pagamento, Integer> {
}
