package pl.edu.pwsztar.shapewars.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pwsztar.shapewars.entities.dto.ShapeDto;
import pl.edu.pwsztar.shapewars.services.ShapeService;

@RestController
@RequestMapping("shapes")
public class ShapeController {

    @Autowired
    private ShapeService shapeService;

    @PostMapping("save")
    public ShapeDto save(@RequestBody ShapeDto dto){
        return shapeService.save(dto);
    }
}
