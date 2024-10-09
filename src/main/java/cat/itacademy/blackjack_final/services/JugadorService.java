package cat.itacademy.blackjack_final.services;

import cat.itacademy.blackjack_final.domain.Partida;
import cat.itacademy.blackjack_final.dto.JugadorDto;
import cat.itacademy.blackjack_final.exceptions.ResourceNotFoundException;
import cat.itacademy.blackjack_final.mappers.JugadorMapper;
import cat.itacademy.blackjack_final.persistence.entities.JugadorEntity;
import cat.itacademy.blackjack_final.persistence.repositories.r2dbc.JugadorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JugadorService {

    private final JugadorRepository jugadorRepository;
    private final JugadorMapper jugadorMapper;

    public Mono<JugadorDto> ensurePlayerExists(String playerName) {
        return jugadorRepository.findByNom(playerName)
                .switchIfEmpty(createNewPlayer(playerName))
                .map(jugadorMapper::toDto);
    }

    private Mono<JugadorEntity> createNewPlayer(String playerName) {
        if (playerName == null || playerName.trim().isEmpty()) {
            return Mono.error(new IllegalArgumentException("El nom del jugador no pot estar buit."));
        }
        JugadorEntity newPlayer = new JugadorEntity(null, playerName,0, 0, 0);
        return jugadorRepository.save(newPlayer);
    }

    public Mono<JugadorDto> updatePlayerName(Long playerId, String newName) {
        return jugadorRepository.findById(playerId)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Jugador no trobat amb id: " + playerId)))
                .flatMap(jugadorEntity -> updateAndSavePlayerName(jugadorEntity, newName))
                .map(jugadorMapper::toDto);
    }

    private Mono<JugadorEntity> updateAndSavePlayerName(JugadorEntity jugadorEntity, String newName) {
        jugadorEntity.setNom(newName);
        return jugadorRepository.save(jugadorEntity);
    }

    public Mono<List<JugadorDto>> getPlayerRanking() {
        return jugadorRepository.findAll()
                .map(jugadorMapper::toDto)
                .sort(Comparator.comparingInt(JugadorDto::getAvgGuanyades).reversed())
                .collectList();
    }

    public Mono<Void> actualizarEstadisticasJugador(Partida partida) {
        Long jugadorId = partida.getIdJugador();

        return jugadorRepository.findById(jugadorId)
                .flatMap(jugador -> {
                    switch (partida.getEstat()) {
                        case JUGADOR_GUANYA -> jugador.setGuanyades(jugador.getGuanyades() + 1);
                        case CRUPIER_GUANYA -> jugador.setPerdudes(jugador.getPerdudes() + 1);
                        case EMPAT -> jugador.setEmpatades(jugador.getEmpatades() + 1);
                    }
                    return jugadorRepository.save(jugador).then();
                });
    }

}
