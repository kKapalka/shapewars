package pl.edu.pwsztar.shapewars.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pwsztar.shapewars.entities.dto.FighterDto;
import pl.edu.pwsztar.shapewars.repositories.FighterRepository;
import pl.edu.pwsztar.shapewars.services.FighterService;

@RestController
@RequestMapping("fighters")
public class FighterController {

    @Autowired
    private FighterService fighterService;

    @PostMapping("save")
    public FighterDto save(FighterDto dto){
        return fighterService.save(dto);
    }
}