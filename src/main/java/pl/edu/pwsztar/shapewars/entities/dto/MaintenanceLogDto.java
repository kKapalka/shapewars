package pl.edu.pwsztar.shapewars.entities.dto;

import lombok.Builder;
import lombok.Data;
import pl.edu.pwsztar.shapewars.entities.MaintenanceLog;

@Builder
@Data
public class MaintenanceLogDto {

    private Long id;
    private String messageTime;
    private String informerName;
    private String message;
    private String messageType;

    public static MaintenanceLogDto fromEntity(MaintenanceLog entity){
        return MaintenanceLogDto.builder()
                .id(entity.getID())
                .messageTime(entity.getMessageTime().toString())
                .informerName(entity.getInformer().getLogin())
                .message(entity.getMessage())
                .messageType(entity.getMessageType().name())
                .build();
    }
}
