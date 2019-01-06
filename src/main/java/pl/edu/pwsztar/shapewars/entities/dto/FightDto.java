package pl.edu.pwsztar.shapewars.entities.dto;

import lombok.Builder;
import lombok.Data;
import pl.edu.pwsztar.shapewars.entities.Fight;

@Data
@Builder
public class FightDto {

    private Long id;
    private String playerOne;
    private String playerTwo;
    private String fightStatus;

    public static FightDto fromEntity(Fight fight){
        return FightDto.builder()
                        .id(fight.getID())
                       .playerOne(fight.getPlayerOne().getLogin())
                       .playerTwo(fight.getPlayerTwo().getLogin())
                       .fightStatus(fight.getFightStatus().name())
                        .build();
    }
}
