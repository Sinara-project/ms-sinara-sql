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
@RequestMapping("/api/user/registroPonto")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class RegistroPontoController implements RegistroPontoOpenApi {
    private final RegistroPontoService registroPontoService;

    public RegistroPontoController(RegistroPontoService registroPontoService) {
        this.registroPontoService = registroPontoService;
    }

//    Métodos comuns
    @GetMapping("buscarPorId/{id}")
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

    @GetMapping("/horarioEntradaSaida/{id}")
    public Map<String, String> buscarHorarios(@PathVariable Integer id) {
        Map<String, String> horarios = new HashMap<>();
        horarios.put("entrada", registroPontoService.buscarHorarioEntrada(id));
        horarios.put("saida", registroPontoService.buscarHorarioSaida(id));
        return horarios;
    }

    @GetMapping("/listarStatusOperario/{idOperario}")
    public ResponseEntity<Boolean> listarStatusOperario(@PathVariable Integer idOperario) {
        Boolean status = registroPontoService.status(idOperario);
        return ResponseEntity.ok(status);
    }

    @GetMapping("/ultimoTurno/{idOperario}")
    public ResponseEntity<String> getUltimoTurno(@PathVariable Integer idOperario) {
        String turnoFormatado = registroPontoService.listarUltimoTurno(idOperario);
        return ResponseEntity.ok(turnoFormatado);
    }

    @GetMapping("/quantidadeRegistroPonto/{idOperario}")
    public ResponseEntity<Integer> calcularPontosRegistrados(@PathVariable Integer idOperario) {
        int totalPontos = registroPontoService.calcularPontosRegistrados(idOperario);
        return ResponseEntity.ok(totalPontos);
    }

    @GetMapping("/horasTrabalhadas/{idOperario}")
    public ResponseEntity<Map<String, String>> calcularHorasTrabalhadas(@PathVariable Integer idOperario) {
        String horasTrabalhadas = registroPontoService.calcularHorasTrabalhadasNoMes(idOperario);
        Map<String, String> resposta = Map.of("horasTrabalhadas", horasTrabalhadas);
        return ResponseEntity.ok(resposta);
    }

    @GetMapping("/bancoHoras/{idOperario}")
    public ResponseEntity<Map<String, String>> getBancoHoras(@PathVariable Integer idOperario) {
        String bancoHoras = registroPontoService.calcularBancoHoras(idOperario);
        Map<String, String> resposta = Map.of("bancoHoras", bancoHoras);
        return ResponseEntity.ok(resposta);
    }
}
