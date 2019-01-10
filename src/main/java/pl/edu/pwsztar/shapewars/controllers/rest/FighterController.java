package pl.edu.pwsztar.shapewars.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.edu.pwsztar.shapewars.entities.dto.FighterCombatDto;
import pl.edu.pwsztar.shapewars.entities.dto.FighterDto;
import pl.edu.pwsztar.shapewars.repositories.FighterRepository;
import pl.edu.pwsztar.shapewars.services.FighterService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("fighters")
@CrossOrigin
public class FighterController {

    @Autowired
    private FighterService fighterService;

    @PostMapping("save")
    public FighterDto save(@RequestBody FighterDto dto){
        return fighterService.save(dto);
    }

    @GetMapping("{login}")
    public List<FighterDto> getByLogin(@PathVariable String login){
        return fighterService.getFightersByLogin(login).stream().map(FighterDto::fromEntity).collect(Collectors.toList());
    }
    @GetMapping("combat/{login}")
    public List<FighterCombatDto> getCombatantsByLogin(@PathVariable String login){
        return fighterService.getCombatantsByLogin(login).stream().map(FighterCombatDto::fromEntity).sorted((a,b)->(int)(a.getId()-b.getId())).collect(Collectors.toList());
    }
}
