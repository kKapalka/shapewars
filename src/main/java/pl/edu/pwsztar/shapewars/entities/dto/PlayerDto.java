package pl.edu.pwsztar.shapewars.entities.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class PlayerDto {

    private Long id;
    private int level;
    private int xpPoints;
    private List<Long> allFighterIDList;
    private List<Long> currentFighterIDs;
}
