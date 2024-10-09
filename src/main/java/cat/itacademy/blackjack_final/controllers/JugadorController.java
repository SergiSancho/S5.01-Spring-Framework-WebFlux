package cat.itacademy.blackjack_final.controllers;

import cat.itacademy.blackjack_final.dto.JugadorDto;
import cat.itacademy.blackjack_final.services.JugadorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/player")
@RequiredArgsConstructor
@Tag(name = "Controlador de Jugadors", description = "Controlador per gestionar els jugadors de Blackjack")
public class JugadorController {

    private final JugadorService jugadorService;

    @GetMapping("/ranking")
    @Operation(summary = "Obtenir el rànquing de jugadors", description = "Obté el rànquing dels jugadors segons el seu rendiment en les partides de Blackjack.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rànquing de jugadors obtingut correctament."),
            @ApiResponse(responseCode = "204", description = "No hi ha jugadors disponibles.")
    })
    public Mono<ResponseEntity<List<JugadorDto>>> getPlayerRanking() {
        return jugadorService.getPlayerRanking()
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.noContent().build());
    }

    @PutMapping("/{playerId}")
    @Operation(summary = "Actualitzar el nom del jugador", description = "Actualitza el nom d'un jugador existent a Blackjack.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Nom del jugador actualitzat correctament."),
            @ApiResponse(responseCode = "404", description = "Jugador no trobat.")
    })
    public Mono<ResponseEntity<JugadorDto>> updatePlayerName(@PathVariable Long playerId, @RequestBody String newName) {
        return jugadorService.updatePlayerName(playerId, newName)
                .map(ResponseEntity::ok);
    }
}
