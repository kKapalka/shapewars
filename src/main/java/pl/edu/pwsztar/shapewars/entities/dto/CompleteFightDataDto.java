package pl.edu.pwsztar.shapewars.entities.dto;

import lombok.Builder;
import lombok.Data;
import pl.edu.pwsztar.shapewars.entities.Fight;

@Data
@Builder
public class CompleteFightDataDto {
    public Long id;
    public PlayerCombatDto playerOne;
    public PlayerCombatDto playerTwo;
    public String fightStatus;

    public static CompleteFightDataDto fromEntity(Fight fight){
        return CompleteFightDataDto.builder()
                .id(fight.getID())
                .playerOne(PlayerCombatDto.fromEntity(fight.getPlayerOne()))
                .playerTwo(PlayerCombatDto.fromEntity(fight.getPlayerTwo()))
                .fightStatus(fight.getFightStatus().name())
                .build();
    }

}
