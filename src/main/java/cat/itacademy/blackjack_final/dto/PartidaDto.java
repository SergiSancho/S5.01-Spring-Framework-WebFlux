package cat.itacademy.blackjack_final.dto;

import cat.itacademy.blackjack_final.domain.Carta;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PartidaDto {

    private String id;
    private Long idJugador;
    private List<Carta> maJugador;
    private int puntuacioJugador;
    private List<Carta> maCrupier;
    private int puntuacioCrupier;
    private String estat;
}
