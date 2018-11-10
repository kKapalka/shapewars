package pl.edu.pwsztar.shapewars.entities.dto;

import lombok.Builder;
import lombok.Data;
import pl.edu.pwsztar.shapewars.entities.Keyword;

@Builder
@Data
public class KeywordDto {

    private Long id;
    private String name;
    private String description;

    public static KeywordDto fromEntity(Keyword keyword){
        return KeywordDto.builder()
                .id(keyword.getID())
                .name(keyword.getName())
                .description(keyword.getExplanation())
                .build();
    }
}
