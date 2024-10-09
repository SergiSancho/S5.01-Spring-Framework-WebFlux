package cat.itacademy.blackjack_final.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JugadorDto {

    private Long id;
    private String nom;
    private int avgGuanyades;
    private int guanyades;
    private int perdudes;
    private int empatades;

}
