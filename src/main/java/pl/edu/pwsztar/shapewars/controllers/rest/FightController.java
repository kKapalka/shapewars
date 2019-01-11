package pl.edu.pwsztar.shapewars.controllers.rest;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import pl.edu.pwsztar.shapewars.entities.dto.CommunicationDto;
import pl.edu.pwsztar.shapewars.entities.dto.FightCombatDto;
import pl.edu.pwsztar.shapewars.entities.dto.FightDto;
import pl.edu.pwsztar.shapewars.entities.dto.TurnOrderDto;
import pl.edu.pwsztar.shapewars.services.FightService;

@RestController
@RequestMapping("fight")
@CrossOrigin
public class FightController {

    @Autowired
    private FightService fightService;

    @PostMapping("save")
    public FightDto save(@RequestBody FightDto dto) throws Exception {
        return fightService.save(dto);
    }

    @GetMapping("all/{login}")
    public List<FightDto> findByUser(@PathVariable String login){
        return fightService.findByUser(login).stream().map(FightDto::fromEntity).collect(Collectors.toList());
    }

    @GetMapping("challenges/{login}")
    public List<FightDto> findChallengesByUser(@PathVariable String login){
        return fightService.getChallengesForUser(login).stream().map(FightDto::fromEntity).collect(Collectors.toList());
    }
    @GetMapping("{login}")
    public FightDto findByChallenger(@PathVariable String login){
        return FightDto.fromEntity(fightService.findByChallenger(login));
    }
    @PostMapping("/turn-order/{turn}")
    public List<TurnOrderDto> getTurnOrder(@RequestBody FightCombatDto dto, @PathVariable Long turn){
        return fightService.getTurnOrderForFight(dto,turn).stream().map(TurnOrderDto::fromEntity).collect(Collectors.toList());
    }
}
