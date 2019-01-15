package pl.edu.pwsztar.shapewars.controllers.rest;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import pl.edu.pwsztar.shapewars.entities.dto.*;
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
    @GetMapping("challenger/{login}")
    public FightDto findByChallenger(@PathVariable String login){
        return FightDto.fromEntity(fightService.findByChallenger(login));
    }
    @GetMapping("{id}")
    public FightDto findById(@PathVariable Long id){
        return FightDto.fromEntity(fightService.findFightById(id));
    }

    @PostMapping("/turn-order/{turn}")
    public List<TurnOrderDto> getTurnOrder(@RequestBody FightCombatDto dto, @PathVariable Long turn){
        return fightService.getTurnOrderForFight(dto,turn).stream().map(TurnOrderDto::fromEntity).collect(Collectors.toList());
    }

    @GetMapping("active/{login}")
    public CompleteFightDataDto findFightInProgressForUser(@PathVariable String login){
        return CompleteFightDataDto.fromEntity(fightService.findFightInProgressForUser(login));
    }

    @GetMapping("bot-fight/{login}")
    public FightDto generateBotFight(@PathVariable String login){
        try {
            return FightDto.fromEntity(fightService.generateBotFightForUser(login));
        } catch(Exception e) {
            return FightDto.builder().build();
        }
    }
}
