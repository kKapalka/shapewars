package pl.edu.pwsztar.shapewars.controllers.rest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pwsztar.shapewars.entities.dto.ColorMapDto;
import pl.edu.pwsztar.shapewars.services.ColorMapService;

@RestController
@RequestMapping("colormap")
public class ColorMapController {

    @Autowired
    private ColorMapService colorMapService;

    @PostMapping("save")
    public ColorMapDto save(@RequestBody ColorMapDto dto){
        return colorMapService.save(dto);
    }

}
