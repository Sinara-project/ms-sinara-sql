package org.example.sinara.controller;

import jakarta.validation.groups.Default;
import org.example.sinara.dto.request.RegistroPontoRequestDTO;
import org.example.sinara.dto.response.RegistroPontoResponseDTO;
import org.example.sinara.model.RegistroPonto;
import org.example.sinara.open_api.RegistroPontoOpenApi;
import org.example.sinara.service.RegistroPontoService;
import org.example.sinara.validation.OnCreate;
import org.example.sinara.validation.OnPatch;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/registroPonto")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class RegistroPontoController implements RegistroPontoOpenApi {
    private final RegistroPontoService registroPontoService;

    public RegistroPontoController(RegistroPontoService registroPontoService) {
        this.registroPontoService = registroPontoService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<RegistroPontoResponseDTO> buscarRegistroPontoPorId(@PathVariable Integer id) {
        RegistroPontoResponseDTO registroPonto = registroPontoService.buscarPorId(id);
        return ResponseEntity.ok(registroPonto);
    }

    @GetMapping("/listar")
    public List<RegistroPontoResponseDTO> listarRegistroPonto(){
        return registroPontoService.listarRegistroPonto();
    }

    @PostMapping("/inserir")
    public ResponseEntity<String> inserirRegistroPonto(@Validated({OnCreate.class, Default.class})
                                                    @RequestBody RegistroPontoRequestDTO dto) {
        registroPontoService.inserirRegistroPonto(dto);
        return ResponseEntity.ok("Registro de ponto inserido com sucesso!");
    }

    @DeleteMapping("/excluir/{id}")
    public ResponseEntity<String> excluirRegistroPonto(@PathVariable Integer id) {
        registroPontoService.excluirRegistroPonto(id);
        return ResponseEntity.ok("Registro de ponto excluído com sucesso!");
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<String> atualizarRegistroPonto(@PathVariable Integer id,
                                                      @Validated({OnPatch.class, Default.class})
                                                      @RequestBody RegistroPontoRequestDTO dto) {
        registroPontoService.atualizarRegistroPonto(id, dto);
        return ResponseEntity.ok("Registro de ponto atualizado com sucesso!");
    }

    @GetMapping("/horario-entrada-saida/{id}")
    public Map<String, String> buscarHorarios(@PathVariable Integer id) {
        Map<String, String> horarios = new HashMap<>();
        horarios.put("entrada", registroPontoService.buscarHorarioEntrada(id));
        horarios.put("saida", registroPontoService.buscarHorarioSaida(id));
        return horarios;
    }

    @GetMapping("/listar-status-operario/{idOperario}")
    public ResponseEntity<Boolean> listarStatusOperario(@PathVariable Integer idOperario) {
        Boolean status = registroPontoService.status(idOperario);
        return ResponseEntity.ok(status);
    }

    @GetMapping("/ultimo-turno/{idOperario}")
    public ResponseEntity<String> getUltimoTurno(@PathVariable Integer idOperario) {
        String turnoFormatado = registroPontoService.listarUltimoTurno(idOperario);
        return ResponseEntity.ok(turnoFormatado);
    }

    @GetMapping("/quantidade-registro-ponto/{idOperario}")
    public ResponseEntity<Integer> calcularPontosRegistrados(@PathVariable Integer idOperario) {
        int totalPontos = registroPontoService.calcularPontosRegistrados(idOperario);
        return ResponseEntity.ok(totalPontos);
    }

    @GetMapping("/horas-trabalhadas/{idOperario}")
    public ResponseEntity<Map<String, String>> calcularHorasTrabalhadas(@PathVariable Integer idOperario) {
        String horasTrabalhadas = registroPontoService.calcularHorasTrabalhadasNoMes(idOperario);
        Map<String, String> resposta = Map.of("horasTrabalhadas", horasTrabalhadas);
        return ResponseEntity.ok(resposta);
    }

    @GetMapping("/banco-horas/{idOperario}")
    public ResponseEntity<Map<String, String>> getBancoHoras(@PathVariable Integer idOperario) {
        String bancoHoras = registroPontoService.calcularBancoHoras(idOperario);
        Map<String, String> resposta = Map.of("bancoHoras", bancoHoras);
        return ResponseEntity.ok(resposta);
    }
}
