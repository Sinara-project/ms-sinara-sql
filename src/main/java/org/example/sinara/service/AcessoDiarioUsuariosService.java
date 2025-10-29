package org.example.sinara.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.example.sinara.model.AcessoDiarioUsuarios;
import org.example.sinara.repository.sql.AcessoDiarioUsuariosRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AcessoDiarioUsuariosService {
    private final AcessoDiarioUsuariosRepository acessoDiarioUsuariosRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public AcessoDiarioUsuariosService(AcessoDiarioUsuariosRepository acessoDiarioUsuariosRepository) {
        this.acessoDiarioUsuariosRepository = acessoDiarioUsuariosRepository;
    }

    public List<AcessoDiarioUsuarios> listarTodos() {
        return acessoDiarioUsuariosRepository.findAll();
    }

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
