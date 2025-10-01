package com.diogo.bffagendadortarefas.controller;



import com.diogo.bffagendadortarefas.business.TarefasService;
import com.diogo.bffagendadortarefas.business.dto.in.TarefasDTORequest;
import com.diogo.bffagendadortarefas.business.dto.out.TarefasDTOResponse;
import com.diogo.bffagendadortarefas.infrastructure.enums.StatusNotificacaoEnum;
import com.diogo.bffagendadortarefas.infrastructure.security.SecurityConfig;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/tarefas")
@RequiredArgsConstructor
@Tag(name = "Tarefas", description = "Cadastra tarefas de usuários")
@SecurityRequirement(name = SecurityConfig.SECURITY_SCHEME)
public class TarefasController {

    private final TarefasService tarefasService;

    @PostMapping
    @Operation(summary = "Salvar Tarefas de Usuários", description = "Cria uma nova tarefa")
    @ApiResponse(responseCode = "200", description = "Tarefa salva com sucesso")
    @ApiResponse(responseCode = "500", description = "Erro no servidor")
    public ResponseEntity <TarefasDTOResponse>  gravarTarefas(@RequestBody TarefasDTORequest dto,
                                                              @RequestHeader(name = "Authorization", required = false) String token){

      return ResponseEntity.ok(tarefasService.gravarTarefa(token, dto));
    }

    @GetMapping("/eventos")
    @Operation(summary = "Busca tarefas por Período", description = "Busca tarefas cadastradas por período")
    @ApiResponse(responseCode = "200", description = "Tarefas encontradas")
    @ApiResponse(responseCode = "500", description = "Erro no servidor")
    public ResponseEntity<List<TarefasDTOResponse>> buscaListadeTarefasPorPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)LocalDateTime dataInicial,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)LocalDateTime dataFinal,
            @RequestHeader(name = "Authorization", required = false) String token){

        return ResponseEntity.ok(tarefasService.buscaTarefasAgendadasPorPeriodo(dataInicial, dataFinal, token));
    }


    @GetMapping
    @Operation(summary = "Busca lista tarefas por email de Usuário", description = "Busca tarefas cadastradas por usuário")
    @ApiResponse(responseCode = "200", description = "Tarefas encontradas")
    @ApiResponse(responseCode = "500", description = "Erro no servidor")
    public ResponseEntity<List<TarefasDTOResponse>> buscaTarefasPorEmail(@RequestHeader(name = "Authorization", required = false) String token){
        return ResponseEntity.ok(tarefasService.buscaTarefasPorEmail(token));
    }

    @DeleteMapping
    @Operation(summary = "Deleta tarefas por Id", description = "Deleta tarefas cadastradas por ID")
    @ApiResponse(responseCode = "200", description = "Tarefas deletadas")
    @ApiResponse(responseCode = "500", description = "Erro no servidor")
    public ResponseEntity<Void> deletaTarefaPorId (@RequestParam("id") String id,
                                                   @RequestHeader(name = "Authorization", required = false) String token){

        tarefasService.deletaTarefaPorId(id, token);

        return ResponseEntity.ok().build();
    }

    @PatchMapping
    @Operation(summary = "Altera status de tarefa", description = "Altera status das tarefas cadastradas")
    @ApiResponse(responseCode = "200", description = "Status da tarefa alterado")
    @ApiResponse(responseCode = "500", description = "Erro no servidor")
    public ResponseEntity<TarefasDTOResponse> alteraStatusNotificacao(@RequestParam("status")StatusNotificacaoEnum status,
                                                                      @RequestParam("id") String id,
                                                                      @RequestHeader(name = "Authorization", required = false) String token){
        return ResponseEntity.ok(tarefasService.alteraStatus(status, id, token));
    }

    @PutMapping
    @Operation(summary = "Altera dados de tarefa", description = "Altera dados das tarefas cadastradas")
    @ApiResponse(responseCode = "200", description = "Tarefas alteradas")
    @ApiResponse(responseCode = "500", description = "Erro no servidor")
    public ResponseEntity<TarefasDTOResponse> updateTarefas(@RequestBody TarefasDTORequest dto,
                                                            @RequestParam("id") String id,
                                                            @RequestHeader(name = "Authorization", required = false) String token){
        return ResponseEntity.ok(tarefasService.updateTarefas(dto, id, token));
    }

}
