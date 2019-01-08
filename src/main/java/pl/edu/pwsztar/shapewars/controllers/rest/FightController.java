package pl.edu.pwsztar.shapewars.controllers.rest;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import pl.edu.pwsztar.shapewars.entities.dto.CommunicationDto;
import pl.edu.pwsztar.shapewars.entities.dto.FightDto;
import pl.edu.pwsztar.shapewars.services.FightService;

@RestController
@RequestMapping("fight")
@CrossOrigin
public class FightController {

    @Autowired
    private FightService fightService;

    @PostMapping("save")
    public FightDto save(@RequestBody FightDto dto){
        return fightService.save(dto);
    }

    @GetMapping("all/{login}")
    public List<FightDto> findByUser(@PathVariable String login){
        return fightService.findByUser(login).stream().map(FightDto::fromEntity).collect(Collectors.toList());
    }
}
