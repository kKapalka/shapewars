package pl.edu.pwsztar.shapewars.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.edu.pwsztar.shapewars.entities.dto.CommunicationDto;
import pl.edu.pwsztar.shapewars.entities.dto.MessageDto;
import pl.edu.pwsztar.shapewars.services.MessageService;

import java.util.List;

@RequestMapping("message")
@RestController
@CrossOrigin
public class MessageController {

    @Autowired
    private MessageService messageService;

    @PostMapping("save")
    public MessageDto save(@RequestBody MessageDto dto){
        return messageService.save(dto);
    }

    @PostMapping("get")
    public List<MessageDto> getAllMessagesByCallers(@RequestBody CommunicationDto dto){
        return messageService.getAllMessagesByCallers(dto);
    }
}
