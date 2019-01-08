package pl.edu.pwsztar.shapewars.entities.dto;

import lombok.Builder;
import lombok.Data;
import pl.edu.pwsztar.shapewars.entities.Changelog;

@Data
@Builder
public class ChangelogDto {

    private Long id;
    private String changeTime;
    private String change;

    public static ChangelogDto fromEntity(Changelog changelog){
        return ChangelogDto.builder()
                          .id(changelog.getID())
                          .changeTime(changelog.getChangeTime().toString())
                          .change(changelog.getChange())
                          .build();
    }

}
