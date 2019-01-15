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
        if(fight!=null) {
            return FightDto.builder()
                    .id(fight.getID())
                    .playerOne(fight.getPlayerOne().getLogin())
                    .playerTwo(fight.getPlayerTwo()!=null?fight.getPlayerTwo().getLogin():null)
                    .fightStatus(fight.getFightStatus().name())
                    .build();
        } else{
            return null;
        }
    }
}
