package org.example.sinara.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class DailyActivityReportService {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void registrarDailyActivity(LocalDate data, String atividades, Integer idOperario, Integer idEmpresa) {
        entityManager.createNativeQuery("CALL registrar_daily_activity_report(:data, :atividades, :idOperario, :idEmpresa)")
                .setParameter("data", data)
                .setParameter("atividades", atividades)
                .setParameter("idOperario", idOperario)
                .setParameter("idEmpresa", idEmpresa)
                .executeUpdate();
    }
}
