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
    private List<Long> allFighterIDList;

    public static PlayerDto fromEntity(User user){
        return PlayerDto.builder()
                .id(user.getID())
                .level(user.getLevel().intValue())
                .allFighterIDList(user.getFighterList().stream().map(Fighter::getID).collect(Collectors.toList()))
                .build();
    }
}
