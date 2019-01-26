package pl.edu.pwsztar.shapewars.entities.dto;

import lombok.Builder;
import lombok.Data;
import pl.edu.pwsztar.shapewars.entities.Fight;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class CompleteFightDataDto {
    public Long id;
    public List<PlayerCombatDto> players;
    public String fightStatus;
    public String winnerName;

    public static CompleteFightDataDto fromEntity(Fight fight){
        return CompleteFightDataDto.builder()
                .id(fight.getID())
                .players(fight.getFightingPlayers().stream().map(PlayerCombatDto::fromEntity).collect(Collectors.toList()))
                .fightStatus(fight.getFightStatus().name())
                .winnerName(fight.getWinnerName())
                .build();
    }

}
