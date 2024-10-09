package cat.itacademy.blackjack_final.persistence.entities;

import cat.itacademy.blackjack_final.domain.Carta;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "games")
public class PartidaEntity {

    @Id
    private String id;
    private Long idJugador;
    private BarallaEntity baralla;
    private List<Carta> maJugador;
    private int puntuacioJugador;
    private List<Carta> maCrupier;
    private int puntuacioCrupier;
    private String estat;
}