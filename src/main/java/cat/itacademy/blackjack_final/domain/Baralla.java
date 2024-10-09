package cat.itacademy.blackjack_final.domain;

import lombok.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
public class Baralla {

    private List<Carta> cartes;

    public Baralla() {
        this.cartes = new ArrayList<>();
    }

    public void inicialitzarBaralla() {
        cartes = new ArrayList<>();
        for (Carta.Pal pal : Carta.Pal.values()) {
            for (Carta.Rang rang : Carta.Rang.values()) {
                cartes.add(new Carta(rang, pal));
            }
        }
        barrejar();
    }

    public void barrejar() {
        Collections.shuffle(cartes);
    }

    public Carta extraerCarta() {
        if (cartes.isEmpty()) {
            throw new IllegalStateException("No hi ha més cartes a la baralla.");
        }
        return cartes.remove(cartes.size() - 1);
    }

    public List<Carta> repartirCartes(int numeroCartes) {
        if (numeroCartes <= 0) {
            throw new IllegalArgumentException("El número de cartes ha de ser positiu.");
        }
        if (numeroCartes > cartes.size()) {
            throw new IllegalStateException("No hi ha suficients cartes a la baralla.");
        }

        List<Carta> cartesRepartides = new ArrayList<>();
        for (int i = 0; i < numeroCartes; i++) {
            cartesRepartides.add(extraerCarta());
        }
        return cartesRepartides;
    }

}

