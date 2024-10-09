package cat.itacademy.blackjack_final.services;

import cat.itacademy.blackjack_final.domain.Jugador;
import cat.itacademy.blackjack_final.domain.Partida;
import cat.itacademy.blackjack_final.dto.PartidaDto;
import cat.itacademy.blackjack_final.exceptions.ResourceNotFoundException;
import cat.itacademy.blackjack_final.mappers.PartidaMapper;
import cat.itacademy.blackjack_final.persistence.repositories.mongo.PartidaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class PartidaService {

    private final PartidaRepository partidaRepository;
    private final PartidaMapper partidaMapper;
    private final JugadorService jugadorService;

    public Mono<PartidaDto> createGame(String jugadorNom) {
        return jugadorService.ensurePlayerExists(jugadorNom)
                .flatMap(jugadorDto -> {
                    Jugador jugador = new Jugador(jugadorDto.getId(), jugadorDto.getNom(), 0, 0, 0);
                    Partida newPartida = new Partida(jugador.getId());
                    newPartida.inicializarPartida();

                    return checkAndUpdateStatistics(newPartida)
                            .then(partidaRepository.save(partidaMapper.toEntity(newPartida)))
                            .map(partidaMapper::toDto);
                });
    }


    public Mono<PartidaDto> getPartidaById(String partidaId) {
        return partidaRepository.findById(partidaId)
                .map(partidaMapper::toDto);
    }

    public Mono<PartidaDto> playGame(String partidaId, Partida.Accio accio) {
        return partidaRepository.findById(partidaId)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Partida no trobada amb ID: " + partidaId)))
                .flatMap(partidaEntity -> {
                    Partida partida = partidaMapper.toDomain(partidaEntity);
                    if (partida.getEstat() != Partida.EstatPartida.EN_CURS) {
                        return Mono.error(new IllegalStateException("La partita e finita."));
                    }
                    partida.realitzarJugada(accio);

                    return checkAndUpdateStatistics(partida)
                            .then(partidaRepository.save(partidaMapper.toEntity(partida)))
                            .map(partidaMapper::toDto);
                });
    }

    private Mono<Void> checkAndUpdateStatistics(Partida partida) {
        return partida.getEstat() == Partida.EstatPartida.EN_CURS
                ? Mono.empty()
                : jugadorService.actualizarEstadisticasJugador(partida);
    }

    public Mono<Void> deleteGame(String partidaId) {
        return partidaRepository.findById(partidaId)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Partida no trobada amb ID: " + partidaId)))
                .flatMap(partidaRepository::delete);
    }

}
