package pl.edu.pwsztar.shapewars.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping("get/user/{id}")
    public List<MessageDto> getAllMessagesByUserId(@PathVariable Long id){
        return messageService.getAllMessagesByUserId(id);
    }
}
