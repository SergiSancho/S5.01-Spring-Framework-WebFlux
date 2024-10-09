package cat.itacademy.blackjack_final.controllers;

import cat.itacademy.blackjack_final.domain.Partida;
import cat.itacademy.blackjack_final.dto.PartidaDto;
import cat.itacademy.blackjack_final.exceptions.ResourceNotFoundException;
import cat.itacademy.blackjack_final.services.PartidaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/game")
@RequiredArgsConstructor
@Tag(name = "Controlador de Jocs", description = "Controlador per gestionar les partides de Blackjack.")
public class PartidaController {

    private final PartidaService partidaService;

    @PostMapping("/new")
    @Operation(summary = "Crear una nova partida", description = "Crea una nova partida de Blackjack associada a un jugador.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Partida creada correctament."),
            @ApiResponse(responseCode = "400", description = "El nom del jugador no pot estar buit.")
    })
    public Mono<ResponseEntity<PartidaDto>> createNewGame(@RequestBody String playerName) {
        return partidaService.createGame(playerName)
                .map(game -> ResponseEntity.status(HttpStatus.CREATED).body(game));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtenir detalls de partida", description = "Obté els detalls d'una partida específica de Blackjack.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Detalls de la partida obtinguts correctament."),
            @ApiResponse(responseCode = "404", description = "Partida no trobada.")
    })
    public Mono<ResponseEntity<PartidaDto>> getGameDetails(@PathVariable String id) {
        return partidaService.getPartidaById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping("/{id}/play")
    @Operation(summary = "Realitzar una jugada", description = "Permet que el jugador realitzi una jugada en una partida de Blackjack (CARTA O PLANTARSE)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Jugada realitzada correctament."),
            @ApiResponse(responseCode = "404", description = "Partida no trobada."),
            @ApiResponse(responseCode = "400", description = "La jugada no és vàlida."),
            @ApiResponse(responseCode = "409", description = "El joc ja ha acabat.")
    })
    public Mono<ResponseEntity<PartidaDto>> playGame(@PathVariable String id,
                                                     @RequestBody @Parameter(description = "Acció a realitzar (CARTA o PLANTARSE)")Partida.Accio accio
    ) {
        return partidaService.playGame(id, accio)
                .map(ResponseEntity::ok)
                .onErrorResume(ResourceNotFoundException.class, e -> Mono.just(ResponseEntity.notFound().build()))
                .onErrorResume(IllegalStateException.class, e -> Mono.just(ResponseEntity.status(HttpStatus.CONFLICT).build()));
    }

    @DeleteMapping("/{id}/delete")
    @Operation(summary = "Eliminar una partida", description = "Elimina una partida pel seu ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Partida eliminada correctament."),
            @ApiResponse(responseCode = "404", description = "Partida no trobada.")
    })
    public Mono<ResponseEntity<Void>> deleteGame(@PathVariable String id) {
        return partidaService.deleteGame(id)
                .then(Mono.just(ResponseEntity.noContent().<Void>build()))
                .onErrorResume(ResourceNotFoundException.class, e -> Mono.just(ResponseEntity.notFound().build()));
    }

}
