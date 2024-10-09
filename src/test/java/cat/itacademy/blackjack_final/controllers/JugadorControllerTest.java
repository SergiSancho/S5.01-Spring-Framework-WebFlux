package cat.itacademy.blackjack_final.controllers;

import cat.itacademy.blackjack_final.dto.JugadorDto;
import cat.itacademy.blackjack_final.services.JugadorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static org.mockito.Mockito.*;

class JugadorControllerTest {

    @Mock
    private JugadorService jugadorService;

    @InjectMocks
    private JugadorController jugadorController;

    private JugadorDto jugadorDto;
    private AutoCloseable closeable;

    @BeforeEach
    void setup() {
        closeable = MockitoAnnotations.openMocks(this);
        jugadorDto = new JugadorDto(1L, "Fiodor Dostoyevski", 99, 3, 2, 1);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void testGetPlayerRanking() {
        when(jugadorService.getPlayerRanking()).thenReturn(Mono.just(List.of(jugadorDto)));
        Mono<ResponseEntity<List<JugadorDto>>> result = jugadorController.getPlayerRanking();

        StepVerifier.create(result)
                .expectNextMatches(response -> response.getStatusCode().is2xxSuccessful() &&
                        response.getBody() != null &&
                        response.getBody().size() == 1 &&
                        response.getBody().get(0).getNom().equals("Fiodor Dostoyevski"))
                .verifyComplete();

        verify(jugadorService).getPlayerRanking();
    }

    @Test
    void testGetPlayerRanking_NoPlayers() {

        when(jugadorService.getPlayerRanking()).thenReturn(Mono.empty());

        Mono<ResponseEntity<List<JugadorDto>>> result = jugadorController.getPlayerRanking();

        StepVerifier.create(result)
                .expectNextMatches(response -> response.getStatusCode().is2xxSuccessful() &&
                        response.getStatusCode().value() == 204)
                .verifyComplete();
        verify(jugadorService).getPlayerRanking();
    }

    @Test
    void testUpdatePlayerName() {
        JugadorDto updatedJugadorDto = new JugadorDto(1L, "JugadorActualizado", 99, 3, 2, 1);
        when(jugadorService.updatePlayerName(1L, "JugadorActualizado")).thenReturn(Mono.just(updatedJugadorDto));

        Mono<ResponseEntity<JugadorDto>> result = jugadorController.updatePlayerName(1L, "JugadorActualizado");

        StepVerifier.create(result)
                .expectNextMatches(response -> response.getStatusCode().is2xxSuccessful() &&
                        response.getBody() != null &&
                        response.getBody().getNom().equals("JugadorActualizado"))
                .verifyComplete();

        verify(jugadorService).updatePlayerName(1L, "JugadorActualizado");
    }
}
