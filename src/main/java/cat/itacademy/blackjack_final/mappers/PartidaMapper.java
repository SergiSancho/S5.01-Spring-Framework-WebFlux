package cat.itacademy.blackjack_final.mappers;

import cat.itacademy.blackjack_final.domain.*;
import cat.itacademy.blackjack_final.dto.PartidaDto;
import cat.itacademy.blackjack_final.persistence.entities.PartidaEntity;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface PartidaMapper {

    PartidaDto toDto(PartidaEntity entity);

    PartidaEntity toEntity(Partida partida);

    Partida toDomain(PartidaEntity entity);

    PartidaDto toDto(Partida partida);
}
