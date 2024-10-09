package cat.itacademy.blackjack_final.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Jugador {

    private Long id;
    private String nom;
    private int guanyades;
    private int perdudes;
    private int empatades;
}
