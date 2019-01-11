package pl.edu.pwsztar.shapewars.entities.dto;

import lombok.Builder;
import lombok.Data;
import pl.edu.pwsztar.shapewars.entities.TurnOrder;

@Data
@Builder
public class TurnOrderDto {
    private Long id;
    private Long fightId;
    private Long fighterId;
    private Long turn;
    private Long order;

    public static TurnOrderDto fromEntity(TurnOrder entity){
        return TurnOrderDto.builder()
                .id(entity.getID())
                .fightId(entity.getFight().getID())
                .fighterId(entity.getFighter().getID())
                .turn(entity.getTurn())
                .order(entity.getOrder()).build();
    }
}
