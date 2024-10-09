package cat.itacademy.blackjack_final.persistence.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table("players")
public class JugadorEntity {

    @Id
    private Long id;
    private String nom;
    private int guanyades;
    private int perdudes;
    private int empatades;

    public int calcularAvgGuanyades() {
        int totalPartides = guanyades + perdudes + empatades;
        return totalPartides > 0 ? (guanyades * 100 / totalPartides) : 0;
    }
}
