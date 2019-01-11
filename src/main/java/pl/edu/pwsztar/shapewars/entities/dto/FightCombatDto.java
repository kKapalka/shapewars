package pl.edu.pwsztar.shapewars.entities.dto;

import lombok.Data;

import java.util.List;

@Data
public class FightCombatDto {

    private Long id;
    private Long fightId;
    private List<FighterSpeedDto> fighterSpeedDtos;

}
