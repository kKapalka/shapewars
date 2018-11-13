package pl.edu.pwsztar.shapewars.entities.dto;

import lombok.Builder;
import lombok.Data;
import pl.edu.pwsztar.shapewars.entities.Fight;

@Data
@Builder
public class FightDto {

    private Long id;
    private Long playerOneId;
    private Long playerTwoId;
    private String fightStatus;

    public static FightDto fromEntity(Fight fight){
        return FightDto.builder()
                        .id(fight.getID())
                       .playerOneId(fight.getPlayerOne().getID())
                       .playerTwoId(fight.getPlayerTwo().getID())
                       .fightStatus(fight.getFightStatus().name())
                        .build();
    }
}
