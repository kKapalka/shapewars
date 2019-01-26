package pl.edu.pwsztar.shapewars.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import pl.edu.pwsztar.shapewars.entities.dto.ActionDto;
import pl.edu.pwsztar.shapewars.services.FightActionService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("action")
@CrossOrigin
public class ActionController {

    @Autowired
    private FightActionService fightActionService;

    @PostMapping("save")
    public ActionDto save(@RequestBody ActionDto dto){
        return fightActionService.save(dto);
    }

    @GetMapping("{id}")
    public List<ActionDto> getActionsById(@PathVariable Long id){
        return fightActionService.getActionsForFight(id).stream().map(ActionDto::fromEntity).collect(Collectors.toList());
    }
}
