package cat.itacademy.blackjack_final.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class Partida {

    private String id;
    private Long idJugador;
    private Baralla baralla;
    private List<Carta> maJugador;
    private int puntuacioJugador;
    private List<Carta> maCrupier;
    private int puntuacioCrupier;
    private EstatPartida estat;

    public enum EstatPartida {
        EN_CURS, JUGADOR_GUANYA, CRUPIER_GUANYA, EMPAT
    }

    public enum Accio {
        CARTA, PLANTARSE
    }

    public Partida(Long idJugador) {
        this.idJugador = idJugador;
        this.baralla = new Baralla();
        this.maJugador = new ArrayList<>();
        this.maCrupier = new ArrayList<>();
        this.estat = EstatPartida.EN_CURS;
    }

    public void inicializarPartida() {
        baralla.inicialitzarBaralla();
        maJugador.addAll(baralla.repartirCartes(2));
        maCrupier.addAll(baralla.repartirCartes(2));
        puntuacioJugador = calcularPuntuacio(maJugador);
        puntuacioCrupier = calcularPuntuacio(maCrupier);
        actualitzarEstatPartida();
    }

    public int calcularPuntuacio(List<Carta> ma) {
        int puntuacio = 0;
        int comptadorAses = 0;

        for (Carta carta : ma) {
            puntuacio += carta.getRang().getValor();
            if (carta.getRang() == Carta.Rang.AS) {
                comptadorAses++;
            }
        }
        while (puntuacio > 21 && comptadorAses > 0) {
            puntuacio -= 10;
            comptadorAses--;
        }
        return puntuacio;
    }

    public void realitzarJugada(Accio accio) {
        if (estat != EstatPartida.EN_CURS) {
            throw new IllegalStateException("La partida c'est finit.");
        }
        if (accio == Accio.CARTA) {
            maJugador.add(baralla.extraerCarta());
            puntuacioJugador = calcularPuntuacio(maJugador);
            actualitzarEstatPartida();
        } else if (accio == Accio.PLANTARSE) {
            tornCrupier();
            actualitzarEstatPartida();
            if (estat == EstatPartida.EN_CURS) {
                if (puntuacioJugador > puntuacioCrupier) {
                    estat = EstatPartida.JUGADOR_GUANYA;
                } else if (puntuacioJugador < puntuacioCrupier) {
                    estat = EstatPartida.CRUPIER_GUANYA;
                } else {
                    estat = EstatPartida.EMPAT;
                }
            }
        } else {
            throw new IllegalArgumentException("Acció no válida.");
        }
    }

    public void tornCrupier() {
        while (puntuacioCrupier < 17) {
            maCrupier.add(baralla.extraerCarta());
            puntuacioCrupier = calcularPuntuacio(maCrupier);
        }
    }

    public void actualitzarEstatPartida() {
        boolean jugadorSePasa = puntuacioJugador > 21;
        boolean crupierSePasa = puntuacioCrupier > 21;
        boolean jugadorBlackjackNatural = puntuacioJugador == 21 && maJugador.size() == 2;
        boolean crupierBlackjackNatural = puntuacioCrupier == 21 && maCrupier.size() == 2;

        if (jugadorSePasa) {
            estat = EstatPartida.CRUPIER_GUANYA;
        } else if (crupierSePasa) {
            estat = EstatPartida.JUGADOR_GUANYA;
        } else if (jugadorBlackjackNatural && crupierBlackjackNatural) {
            estat = EstatPartida.EMPAT;
        } else if (jugadorBlackjackNatural) {
            estat = EstatPartida.JUGADOR_GUANYA;
        } else if (crupierBlackjackNatural) {
            estat = EstatPartida.CRUPIER_GUANYA;
        } else if (maCrupier.size() >= 2 && puntuacioCrupier >= 17) {
            if (puntuacioJugador > puntuacioCrupier) {
                estat = EstatPartida.JUGADOR_GUANYA;
            } else if (puntuacioJugador < puntuacioCrupier) {
                estat = EstatPartida.EN_CURS;
            } else if (puntuacioJugador == puntuacioCrupier) {
                estat = EstatPartida.EMPAT;
            }
        } else {
            estat = EstatPartida.EN_CURS;
        }
    }

}
