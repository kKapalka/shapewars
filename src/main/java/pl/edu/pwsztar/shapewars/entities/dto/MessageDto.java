package pl.edu.pwsztar.shapewars.entities.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class MessageDto {

    private Long id;
    private Long senderId;
    private Long receiverId;
    private String messageTime;
    private String message;
}
