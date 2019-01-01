package pl.edu.pwsztar.shapewars.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping("user/{id}")
    public List<FighterDto> getByUserId(@PathVariable Long id){
        return fighterService.getFightersByUserId(id).stream().map(FighterDto::fromEntity).collect(Collectors.toList());
    }

}
