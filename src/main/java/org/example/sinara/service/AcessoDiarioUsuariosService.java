package org.example.sinara.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.example.sinara.model.AcessoDiarioUsuarios;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AcessoDiarioUsuariosService {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void registrar(AcessoDiarioUsuarios request) {
        entityManager.createNativeQuery("CALL registrar_acesso_diario_usuarios(:data, :atividades, :idOperario, :idEmpresa)")
                .setParameter("data", request.getData())
                .setParameter("atividades", request.getAtividades())
                .setParameter("idOperario", request.getIdOperario())
                .setParameter("idEmpresa", request.getIdEmpresa())
                .executeUpdate();
    }
}
