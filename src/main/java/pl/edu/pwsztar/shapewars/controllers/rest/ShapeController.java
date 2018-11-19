package pl.edu.pwsztar.shapewars.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.edu.pwsztar.shapewars.entities.dto.ShapeDto;
import pl.edu.pwsztar.shapewars.services.ShapeService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("shapes")
public class ShapeController {

    @Autowired
    private ShapeService shapeService;

    @PostMapping("save")
    public ShapeDto save(@RequestBody ShapeDto dto){
        return shapeService.save(dto);
    }

    @GetMapping("all")
    public List<ShapeDto> getAll(){
        return shapeService.getAll().stream().map(ShapeDto::fromEntity).collect(Collectors.toList());
    }
}
