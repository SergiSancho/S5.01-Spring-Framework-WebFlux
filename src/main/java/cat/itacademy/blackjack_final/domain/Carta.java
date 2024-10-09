package cat.itacademy.blackjack_final.domain;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Data
public class Carta {
    public enum Pal {
        CORS, DIAMANTS, TREVOLS, PIQUES
    }


    @RequiredArgsConstructor
    @Getter
    public enum Rang {
        DOS(2), TRES(3), QUATRE(4), CINC(5), SIS(6),
        SET(7), VUIT(8), NOU(9), DEU(10),
        J(10), Q(10), K(10), AS(11);

        private final int valor;
    }

    private final Rang rang;
    private final Pal pal;
}