package pl.edu.pwsztar.shapewars.controllers.rest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.edu.pwsztar.shapewars.entities.dto.ColorMapDto;
import pl.edu.pwsztar.shapewars.services.ColorMapService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("colormap")
@CrossOrigin
public class ColorMapController {

    @Autowired
    private ColorMapService colorMapService;

    @PostMapping("save")
    public ColorMapDto save(@RequestBody ColorMapDto dto){
        return colorMapService.save(dto);
    }

    @GetMapping("all")
    public List<ColorMapDto> getAll(){
        return colorMapService.getAll().stream().map(ColorMapDto::fromEntity).collect(Collectors.toList());
    }
    @GetMapping("{id}")
    public ColorMapDto getById(@PathVariable Long id){
        return ColorMapDto.fromEntity(colorMapService.getColorMapById(id));
    }

}
