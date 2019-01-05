package pl.edu.pwsztar.shapewars.entities.dto;

import lombok.Builder;
import lombok.Data;
import pl.edu.pwsztar.shapewars.entities.Message;

@Builder
@Data
public class MessageDto {
    private Long id;
    private String sender;
    private String receiver;
    private String messageTime;
    private String message;

    public static MessageDto fromEntity(Message message){
        return MessageDto.builder()
                .id(message.getID())
                .sender(message.getSender().getLogin())
                .receiver(message.getReceiver().getLogin())
                .messageTime(message.getMessageTime().toString())
                .message(message.getMessage())
                .build();
    }
}
