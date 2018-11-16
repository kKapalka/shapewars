package pl.edu.pwsztar.shapewars.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.edu.pwsztar.shapewars.entities.dto.PlayerDto;
import pl.edu.pwsztar.shapewars.services.UserService;

import java.util.List;

@RequestMapping("player")
@RestController
@CrossOrigin("*")
public class PlayerController {

    @Autowired
    private UserService userService;

    @PostMapping("add-fighters/{id}")
    public PlayerDto addFighters(@RequestBody List<Long> list, @PathVariable Long id){
        return PlayerDto.fromEntity(userService.addFightersToUser(id,list));
    }

    @GetMapping("/{login}")
    public PlayerDto get(@PathVariable String login){
        return PlayerDto.fromEntity(userService.getUserByLogin(login));
    }


}
