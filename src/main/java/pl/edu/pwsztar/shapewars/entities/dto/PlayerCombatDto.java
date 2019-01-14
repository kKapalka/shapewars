package pl.edu.pwsztar.shapewars.entities.dto;

import lombok.Builder;
import lombok.Data;
import pl.edu.pwsztar.shapewars.entities.User;
import pl.edu.pwsztar.shapewars.entities.enums.FighterSlot;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class PlayerCombatDto {

    private Long id;
    private String login;
    private Long level;
    private Long xpPoints;
    private List<FighterCombatDto> allFighterList;

    public static PlayerCombatDto fromEntity(User entity){
        return PlayerCombatDto.builder()
                .id(entity.getID())
                .login(entity.getLogin())
                .level(entity.getLevel())
                .xpPoints(entity.getExperiencePoints())
                .allFighterList(entity.getFighterList().stream()
                        .filter(fighter->fighter.getSlot()!= FighterSlot.INVENTORY)
                        .map(FighterCombatDto::fromEntity).collect(Collectors.toList()))
                .build();
    }
}
