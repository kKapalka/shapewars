package pl.edu.pwsztar.shapewars.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.edu.pwsztar.shapewars.entities.dto.AgentDto;
import pl.edu.pwsztar.shapewars.entities.dto.CompleteFightDataDto;
import pl.edu.pwsztar.shapewars.entities.dto.FightDto;
import pl.edu.pwsztar.shapewars.services.AgentService;

@RestController
@RequestMapping("agent")
@CrossOrigin
public class AgentController {

    @Autowired
    private AgentService agentService;

    @GetMapping("{username}")
    public AgentDto getAgentByPlayerName(@PathVariable String username){
        return agentService.getAgentByPlayerName(username);
    }

    @PostMapping("update")
    public void updateAgentLearningSet(@RequestBody CompleteFightDataDto dto){
        agentService.updateAgentLearningSet(dto);
    }

    @PostMapping("finish")
    public void onBattleFinish(@RequestBody FightDto dto){
        agentService.onBattleFinish(dto);
    }

}
