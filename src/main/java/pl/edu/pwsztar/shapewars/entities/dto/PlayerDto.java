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
    private String login;
    private Boolean admin;
    private int level;
    private int xpPoints;
    private List<FighterBaseDto> allFighterList;

    public static PlayerDto fromEntity(User user){
        return PlayerDto.builder()
                .id(user.getID())
                .login(user.getLogin())
                .admin(user.isAdmin())
                .level(user.getLevel().intValue())
                .allFighterList(user.getFighterList().stream().map(FighterBaseDto::fromEntity).collect(Collectors.toList()))
                .build();
    }
}
