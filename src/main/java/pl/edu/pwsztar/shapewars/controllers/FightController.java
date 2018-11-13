package pl.edu.pwsztar.shapewars.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pl.edu.pwsztar.shapewars.entities.dto.FightDto;
import pl.edu.pwsztar.shapewars.services.FightService;

@RestController
@RequestMapping("fight")
public class FightController {

    @Autowired
    private FightService fightService;

    @PostMapping("save")
    public FightDto save(@RequestBody FightDto dto){
        return fightService.save(dto);
    }
}
