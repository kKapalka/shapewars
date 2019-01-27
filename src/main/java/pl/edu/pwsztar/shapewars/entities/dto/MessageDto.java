package pl.edu.pwsztar.shapewars.entities.dto;

import lombok.Builder;
import lombok.Data;
import pl.edu.pwsztar.shapewars.entities.Message;
import pl.edu.pwsztar.shapewars.entities.User;

import java.util.List;
import java.util.stream.Collectors;

@Builder
@Data
public class MessageDto {
    private Long id;
    private List<String> messagePlayers;
    private String messageTime;
    private String message;
    private String senderName;

    public static MessageDto fromEntity(Message message){
        return MessageDto.builder()
                .id(message.getID())
                .messagePlayers(message.getMessagePlayers().stream().map(User::getLogin).collect(Collectors.toList()))
                .messageTime(message.getMessageTime().toString())
                .message(message.getMessage())
                .senderName(message.getSenderName())
                .build();
    }
}
