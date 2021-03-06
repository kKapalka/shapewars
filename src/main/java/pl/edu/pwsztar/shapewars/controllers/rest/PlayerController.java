package pl.edu.pwsztar.shapewars.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.edu.pwsztar.shapewars.entities.dto.PlayerDto;
import pl.edu.pwsztar.shapewars.services.UserService;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("player")
@RestController
@CrossOrigin()
public class PlayerController {

    @Autowired
    private UserService userService;

    @GetMapping("all")
    public List<PlayerDto> getAll(){
        return userService.getAll().stream().map(PlayerDto::fromEntity).collect(Collectors.toList());
    }

    @PostMapping("add-fighters/{id}")
    public PlayerDto addFighters(@RequestBody List<Long> list, @PathVariable Long id){
        return PlayerDto.fromEntity(userService.addFightersToUser(id,list));
    }

    @GetMapping("/{login}")
    public PlayerDto get(@PathVariable String login){
        return PlayerDto.fromEntity(userService.getUserByLogin(login));
    }

    @GetMapping("/generate?lvl={level}")
    public PlayerDto generateOpponentWithLevel(@PathVariable Long level){
        return PlayerDto.fromEntity(userService.generateOpponentWithLevel(level));
    }

    @GetMapping("/friends/{login}")
    public List<PlayerDto> getFriendsByLogin(@PathVariable String login){
        return userService.getFriendsByLogin(login).stream().map(PlayerDto::fromEntity).collect(Collectors.toList());
    }
}
