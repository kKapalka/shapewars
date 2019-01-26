package pl.edu.pwsztar.shapewars.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.edu.pwsztar.shapewars.entities.Message;
import pl.edu.pwsztar.shapewars.entities.User;
import pl.edu.pwsztar.shapewars.entities.dto.CommunicationDto;
import pl.edu.pwsztar.shapewars.entities.dto.MessageDto;
import pl.edu.pwsztar.shapewars.repositories.MessageRepository;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserService userService;

    public MessageDto save(MessageDto dto){
        Message message = updateMessage(dto);
        return MessageDto.fromEntity(messageRepository.save(message));
    }

    private Message updateMessage(MessageDto dto){
        Message message = new Message();
        if(dto.getId()!=null){
            message = messageRepository.getOne(dto.getId());
        }
        message.setMessage(dto.getMessage());
        message.setMessageTime(LocalDateTime.now());
        message.setSender(userService.getUserByLogin(dto.getSender()));
        message.setReceiver(userService.getUserByLogin(dto.getReceiver()));
        return message;
    }

    public List<MessageDto> getAllMessagesByCallers(CommunicationDto dto){
        List<MessageDto> list = messageRepository.getAllByCallers(dto.getCallers())
                .stream().map(MessageDto::fromEntity).collect(Collectors.toList());
        return list;
    }
    public List<MessageDto> getAllMessagesByUserName(String login){
        return messageRepository.getAllBySenderOrReceiver(login).stream().map(MessageDto::fromEntity).collect(Collectors.toList());
    }
}
