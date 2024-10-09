package cat.itacademy.blackjack_final.mappers;

import cat.itacademy.blackjack_final.domain.Jugador;
import cat.itacademy.blackjack_final.dto.JugadorDto;
import cat.itacademy.blackjack_final.persistence.entities.JugadorEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface JugadorMapper {

    @Mapping(target = "avgGuanyades", expression = "java(entity.calcularAvgGuanyades())")
    JugadorDto toDto(JugadorEntity entity);

    JugadorEntity toEntity(Jugador jugador);

    Jugador toDomain(JugadorEntity entity);

    JugadorDto toDto(Jugador jugador);
}
