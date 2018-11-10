package pl.edu.pwsztar.shapewars.entities.dto;

import lombok.Builder;
import lombok.Data;
import pl.edu.pwsztar.shapewars.entities.Message;

@Builder
@Data
public class MessageDto {
    private Long id;
    private Long senderId;
    private Long receiverId;
    private String messageTime;
    private String message;

    public static MessageDto fromEntity(Message message){
        return MessageDto.builder()
                .id(message.getID())
                .senderId(message.getSender().getID())
                .receiverId(message.getReceiver().getID())
                .messageTime(message.getMessageTime().toString())
                .message(message.getMessage())
                .build();
    }
}
