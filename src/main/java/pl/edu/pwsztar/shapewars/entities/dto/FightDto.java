package pl.edu.pwsztar.shapewars.entities.dto;

import lombok.Builder;
import lombok.Data;
import pl.edu.pwsztar.shapewars.entities.Fight;
import pl.edu.pwsztar.shapewars.entities.User;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class FightDto {

    private Long id;
    private List<String> playerNames;
    private String fightStatus;
    private String winnerName;

    public static FightDto fromEntity(Fight fight){
        if(fight!=null) {
            return FightDto.builder()
                    .id(fight.getID())
                    .playerNames(fight.getFightingPlayers().stream().map(User::getLogin).collect(Collectors.toList()))
                    .fightStatus(fight.getFightStatus().name())
                    .winnerName(fight.getWinnerName())
                    .build();
        } else{
            return null;
        }
    }
}
