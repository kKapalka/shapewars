package pl.edu.pwsztar.shapewars.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import pl.edu.pwsztar.shapewars.entities.dto.ActionDto;
import pl.edu.pwsztar.shapewars.services.ActionService;

@RestController
@RequestMapping("action")
@CrossOrigin
public class ActionController {

    @Autowired
    private ActionService actionService;

    @PostMapping("save")
    public ActionDto save(@RequestBody ActionDto dto){
        return actionService.save(dto);
    }
}
