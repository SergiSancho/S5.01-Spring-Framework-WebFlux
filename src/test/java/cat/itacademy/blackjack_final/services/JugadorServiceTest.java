package cat.itacademy.blackjack_final.services;

import cat.itacademy.blackjack_final.domain.Partida;
import cat.itacademy.blackjack_final.dto.JugadorDto;
import cat.itacademy.blackjack_final.exceptions.ResourceNotFoundException;
import cat.itacademy.blackjack_final.mappers.JugadorMapper;
import cat.itacademy.blackjack_final.persistence.entities.JugadorEntity;
import cat.itacademy.blackjack_final.persistence.repositories.r2dbc.JugadorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class JugadorServiceTest {

    @Mock
    private JugadorRepository jugadorRepository;

    @Mock
    private JugadorMapper jugadorMapper;

    @InjectMocks
    private JugadorService jugadorService;
    private JugadorEntity jugadorEntity;
    private JugadorDto jugadorDto;
    private AutoCloseable closeable;

    @BeforeEach
    void setup() {
        closeable = MockitoAnnotations.openMocks(this); // Inicializar mocks
        jugadorEntity = new JugadorEntity(1L, "Fiodor Dostoyevski", 3, 2, 1);
        jugadorDto = new JugadorDto(1L, "Fiodor Dostoyevski", 99, 3, 2, 1);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void testEnsurePlayerExists_PlayerExists() {
        when(jugadorRepository.findByNom("Fiodor Dostoyevski")).thenReturn(Mono.empty());
        when(jugadorRepository.save(any(JugadorEntity.class))).thenReturn(Mono.just(jugadorEntity));
        when(jugadorMapper.toDto(jugadorEntity)).thenReturn(jugadorDto);

        Mono<JugadorDto> result = jugadorService.ensurePlayerExists("Fiodor Dostoyevski");

        StepVerifier.create(result)
                .expectNext(jugadorDto)
                .verifyComplete();

        verify(jugadorRepository).findByNom("Fiodor Dostoyevski");
        verify(jugadorRepository).save(any(JugadorEntity.class));
        verify(jugadorMapper).toDto(jugadorEntity);
    }

    @Test
    void testEnsurePlayerExists_PlayerDoesNotExist_CreatesNewPlayer() {
        when(jugadorRepository.findByNom("NuevoJugador")).thenReturn(Mono.empty());
        when(jugadorRepository.save(any(JugadorEntity.class))).thenReturn(Mono.just(jugadorEntity));
        when(jugadorMapper.toDto(any(JugadorEntity.class))).thenReturn(jugadorDto);

        Mono<JugadorDto> result = jugadorService.ensurePlayerExists("NuevoJugador");

        StepVerifier.create(result)
                .expectNext(jugadorDto)
                .verifyComplete();

        verify(jugadorRepository).findByNom("NuevoJugador");
        verify(jugadorRepository).save(any(JugadorEntity.class));
    }

    @Test
    void testUpdatePlayerName_PlayerExists() {
        when(jugadorRepository.findById(1L)).thenReturn(Mono.just(jugadorEntity));

        JugadorEntity updatedJugadorEntity = new JugadorEntity(1L, "JugadorActualizado", 3, 2, 1);
        when(jugadorRepository.save(any(JugadorEntity.class))).thenReturn(Mono.just(updatedJugadorEntity));

        JugadorDto updatedJugadorDto = new JugadorDto(1L, "JugadorActualizado", updatedJugadorEntity.calcularAvgGuanyades(), 3, 2, 1);
        when(jugadorMapper.toDto(updatedJugadorEntity)).thenReturn(updatedJugadorDto);

        Mono<JugadorDto> result = jugadorService.updatePlayerName(1L, "JugadorActualizado");

        StepVerifier.create(result)
                .expectNext(updatedJugadorDto)
                .verifyComplete();

        verify(jugadorRepository).findById(1L);
        verify(jugadorRepository).save(any(JugadorEntity.class));
    }

    @Test
    void testUpdatePlayerName_PlayerNotFound() {
        when(jugadorRepository.findById(2L)).thenReturn(Mono.empty());

        Mono<JugadorDto> result = jugadorService.updatePlayerName(2L, "JugadorActualizado");

        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable instanceof ResourceNotFoundException
                        && throwable.getMessage().equals("Jugador no trobat amb id: 2"))
                .verify();

        verify(jugadorRepository).findById(2L);
    }

    @Test
    void testGetPlayerRanking() {
        when(jugadorRepository.findAll()).thenReturn(Flux.just(jugadorEntity));
        when(jugadorMapper.toDto(jugadorEntity)).thenReturn(jugadorDto);

        Mono<List<JugadorDto>> result = jugadorService.getPlayerRanking();

        StepVerifier.create(result)
                .expectNextMatches(jugadors -> jugadors.size() == 1 && jugadors.get(0).getNom().equals("Fiodor Dostoyevski"))
                .verifyComplete();

        verify(jugadorRepository).findAll();
    }

    @Test
    void testActualizarEstadisticasJugador() {
        Partida partida = new Partida(1L);
        partida.setEstat(Partida.EstatPartida.JUGADOR_GUANYA);

        when(jugadorRepository.findById(1L)).thenReturn(Mono.just(jugadorEntity));
        when(jugadorRepository.save(any(JugadorEntity.class))).thenReturn(Mono.just(jugadorEntity));

        Mono<Void> result = jugadorService.actualizarEstadisticasJugador(partida);

        StepVerifier.create(result)
                .verifyComplete();

        verify(jugadorRepository).findById(1L);
        verify(jugadorRepository).save(any(JugadorEntity.class));
    }
}