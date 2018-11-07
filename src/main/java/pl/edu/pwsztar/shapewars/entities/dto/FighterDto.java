package pl.edu.pwsztar.shapewars.entities.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class FighterDto {

    private Long id;
    private String shapeName;
    private int level;
    private int xpPoints;
    private List<Long> shapeSkillsetIDs;
    private Long shapeSpecialSkillID;
    private String color;
    private int maximumHp;
    private int currentHp;
    private int damage;
    private int speed;

}
