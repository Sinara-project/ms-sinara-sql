package org.example.sinara.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.example.sinara.dto.request.RegistroPontoRequestDTO;
import org.example.sinara.dto.response.RegistroPontoResponseDTO;
import org.example.sinara.exception.HorarioEntradaIgualSaidaException;
import org.example.sinara.model.Empresa;
import org.example.sinara.model.Operario;
import org.example.sinara.model.RegistroPonto;
import org.example.sinara.repository.sql.EmpresaRepository;
import org.example.sinara.repository.sql.OperarioRepository;
import org.example.sinara.repository.sql.RegistroPontoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class RegistroPontoService {
    private final RegistroPontoRepository registroPontoRepository;
    private final EmpresaRepository empresaRepository;
    private final OperarioRepository operarioRepository;
    private final OperarioService operarioService;

    @Autowired
    public RegistroPontoService(
            RegistroPontoRepository registroPontoRepository,
            EmpresaRepository empresaRepository,
            OperarioRepository operarioRepository,
            OperarioService operarioService
    ) {
        this.registroPontoRepository = registroPontoRepository;
        this.empresaRepository = empresaRepository;
        this.operarioRepository = operarioRepository;
        this.operarioService = operarioService;
    }

    private RegistroPonto toEntity(RegistroPontoRequestDTO dto) {
        RegistroPonto registro = new RegistroPonto();
        registro.setHorarioEntrada(dto.getHorarioEntrada());
        registro.setHorarioSaida(dto.getHorarioSaida());

        Operario operario = operarioRepository.findById(dto.getIdOperario())
                .orElseThrow(() -> new RuntimeException("Operário não encontrado"));
        registro.setOperario(operario);

        Empresa empresa = empresaRepository.findById(dto.getIdEmpresa())
                .orElseThrow(() -> new RuntimeException("Empresa não encontrada"));
        registro.setEmpresa(empresa);

        return registro;
    }

    private RegistroPontoResponseDTO toResponseDTO(RegistroPonto registroPonto) {
        RegistroPontoResponseDTO dto = new RegistroPontoResponseDTO();
        dto.setId(registroPonto.getId());
        dto.setHorarioEntrada(registroPonto.getHorarioEntrada());
        dto.setHorarioSaida(registroPonto.getHorarioSaida());
        dto.setIdOperario(registroPonto.getOperario().getId());
        dto.setIdEmpresa(registroPonto.getEmpresa().getId());
        return dto;
    }


    //Métod0 buscar por id
    public RegistroPontoResponseDTO buscarPorId(Integer id){
        RegistroPonto registroPonto= registroPontoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Registro de ponto não encontrado"));
        return toResponseDTO(registroPonto);
    }

    //Métod0 listar
    public List<RegistroPontoResponseDTO> listarRegistroPonto(){
        return registroPontoRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    //Métod0 inserir
    public RegistroPontoResponseDTO inserirRegistroPonto(RegistroPontoRequestDTO dto) {
        RegistroPonto registroPonto = toEntity(dto);

        if (registroPonto.getHorarioEntrada() != null && registroPonto.getHorarioSaida() != null) {
            if (registroPonto.getHorarioEntrada().equals(registroPonto.getHorarioSaida())) {
                throw new HorarioEntradaIgualSaidaException();
            }
        }

        RegistroPonto salvo = registroPontoRepository.save(registroPonto);
        return toResponseDTO(salvo);
    }
    //Métod0 excluir
    public void excluirRegistroPonto(Integer id) {
        if (!registroPontoRepository.existsById(id)) {
            throw new EntityNotFoundException("Registro de ponto não encontrado");
        }
        registroPontoRepository.deleteById(id);
    }

    //Métod0 atualizar
    public RegistroPontoResponseDTO atualizarRegistroPonto(Integer id, RegistroPontoRequestDTO dto) {
        RegistroPonto registroPonto = registroPontoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Registro de ponto não encontrado"));

        if (dto.getHorarioEntrada() != null) {
            registroPonto.setHorarioEntrada(dto.getHorarioEntrada());
        }
        if (dto.getHorarioSaida() != null) {
            registroPonto.setHorarioSaida(dto.getHorarioSaida());
        }
        if (dto.getIdOperario() != null) {
            Operario operario = operarioRepository.findById(dto.getIdOperario())
                    .orElseThrow(() -> new RuntimeException("Operário não encontrado"));
            registroPonto.setOperario(operario);
        }
        if (dto.getIdEmpresa() != null) {
            Empresa empresa = empresaRepository.findById(dto.getIdEmpresa())
                    .orElseThrow(() -> new RuntimeException("Empresa não encontrada"));
            registroPonto.setEmpresa(empresa);
        }


        RegistroPonto atualizado = registroPontoRepository.save(registroPonto);
        return toResponseDTO(atualizado);
    }

    public String buscarHorarioEntrada(Integer id) {
        RegistroPonto registro = registroPontoRepository.findById(id).orElse(null);
        if (registro == null) return null;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        return registro.getHorarioEntrada().format(formatter);
    }

    public String buscarHorarioSaida(Integer id) {
        RegistroPonto registro = registroPontoRepository.findById(id).orElse(null);
        if (registro == null) return null;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        return registro.getHorarioSaida().format(formatter);
    }

    public boolean status(Integer idOperario) {
        LocalDate hoje = LocalDate.now();

        // pega o registro mais recente do operario
        Optional<RegistroPonto> registroOpt = registroPontoRepository
                .findTopByOperario_IdOrderByHorarioEntradaDesc(idOperario);

        if (registroOpt.isEmpty()) {
            return false;
        }

        RegistroPonto registro = registroOpt.get();

        if (registro.getHorarioEntrada() == null) {
            return false;
        }

        boolean mesmoDia = registro.getHorarioEntrada().toLocalDate().isEqual(hoje);

        boolean bateuEntrada = true;
        boolean naoBateuSaida = registro.getHorarioSaida() == null;

        return mesmoDia && bateuEntrada && naoBateuSaida;
    }

    public String calcularBancoHoras(Integer idOperario) {
        if (!operarioRepository.existsById(idOperario)) {
            throw new EntityNotFoundException("Operário não encontrado com ID: " + idOperario);
        }

        List<RegistroPonto> registros = registroPontoRepository.buscarRegistrosDoMesAtual(idOperario);

        long totalMinutosTrabalhados = 0;

        for (RegistroPonto registro : registros) {
            if (registro.getHorarioEntrada() != null && registro.getHorarioSaida() != null) {
                long minutos = java.time.Duration
                        .between(registro.getHorarioEntrada(), registro.getHorarioSaida())
                        .toMinutes();
                totalMinutosTrabalhados += minutos;
            }
        }

        int horasPrevistas = operarioService.getHorasPrevistas(idOperario);
        long minutosPrevistos = horasPrevistas * 60L;

        long bancoMinutos = totalMinutosTrabalhados - minutosPrevistos;

        long horas = bancoMinutos / 60;
        long minutosRestantes = bancoMinutos % 60;

        return String.format("%02d:%02d", horas, minutosRestantes);
    }

    //  Query
    public String listarUltimoTurno(Integer idOperario) {
        Map<String, Object> turno = registroPontoRepository.buscarUltimoTurnoOperario(idOperario);

        if (turno == null || turno.isEmpty()) {
            throw new EntityNotFoundException("Nenhum turno encontrado para o operário com ID " + idOperario);
        }

        Object horarioSaidaObj = turno.get("horarioSaida");
        if (horarioSaidaObj == null) {
            throw new EntityNotFoundException("Horário de saída não encontrado para o operário com ID " + idOperario);
        }

        LocalDateTime horarioSaida = ((Timestamp) horarioSaidaObj).toLocalDateTime();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm");

        return horarioSaida.format(formatter);
    }

    public int calcularPontosRegistrados(Integer idOperario) {
        return registroPontoRepository.contarPontosPorOperario(idOperario);
    }

    public String calcularHorasTrabalhadasNoMes(Integer idOperario) {
        if (!operarioRepository.existsById(idOperario)) {
            throw new EntityNotFoundException("Operário com ID " + idOperario + " não encontrado");
        }

        List<RegistroPonto> registros = registroPontoRepository.buscarRegistrosDoMesAtual(idOperario);
        long totalMinutos = 0;

        for (RegistroPonto registro : registros) {
            if (registro.getHorarioEntrada() != null && registro.getHorarioSaida() != null) {
                long minutos = java.time.Duration
                        .between(registro.getHorarioEntrada(), registro.getHorarioSaida())
                        .toMinutes();
                totalMinutos += minutos;
            }
        }

        long horas = totalMinutos / 60;
        long minutosRestantes = totalMinutos % 60;

        return String.format("%02d:%02d", horas, minutosRestantes);
    }

}
