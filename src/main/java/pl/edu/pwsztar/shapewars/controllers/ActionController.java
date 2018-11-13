package pl.edu.pwsztar.shapewars.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pl.edu.pwsztar.shapewars.entities.dto.ActionDto;
import pl.edu.pwsztar.shapewars.services.ActionService;

@RestController
@RequestMapping("action")
public class ActionController {

    @Autowired
    private ActionService actionService;

    @PostMapping("save")
    public ActionDto save(@RequestBody ActionDto dto){
        return actionService.save(dto);
    }
}
