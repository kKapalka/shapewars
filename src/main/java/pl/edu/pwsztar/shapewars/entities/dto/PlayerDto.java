package pl.edu.pwsztar.shapewars.entities.dto;

import lombok.Builder;
import lombok.Data;
import pl.edu.pwsztar.shapewars.entities.Fighter;
import pl.edu.pwsztar.shapewars.entities.User;

import java.util.List;
import java.util.stream.Collectors;

@Builder
@Data
public class PlayerDto {

    private Long id;
    private int level;
    private int xpPoints;
    private List<String> allFighterList;

    public static PlayerDto fromEntity(User user){
        return PlayerDto.builder()
                .id(user.getID())
                .level(user.getLevel().intValue())
                .allFighterList(user.getFighterList().stream().map(fighter->
                    fighter.getColor().getColorName().toString().toLowerCase() +" "+ fighter.getShape().getName()
                        +", level "+fighter.getLevel()
                ).collect(Collectors.toList()))
                .build();
    }
}
