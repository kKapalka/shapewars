package pl.edu.pwsztar.shapewars.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import pl.edu.pwsztar.shapewars.entities.dto.ActionDto;
import pl.edu.pwsztar.shapewars.services.ActionService;

import java.util.List;
import java.util.stream.Collectors;

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

    @GetMapping("{id}")
    public List<ActionDto> getActionsById(@PathVariable Long id){
        return actionService.getActionsForFight(id).stream().map(ActionDto::fromEntity).collect(Collectors.toList());
    }
}
