package pl.edu.pwsztar.shapewars.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.pwsztar.shapewars.entities.ColorMap;
import pl.edu.pwsztar.shapewars.entities.Fighter;
import pl.edu.pwsztar.shapewars.entities.dto.ColorMapDto;
import pl.edu.pwsztar.shapewars.entities.dto.FighterDto;
import pl.edu.pwsztar.shapewars.entities.enums.Colors;
import pl.edu.pwsztar.shapewars.repositories.ColorMapRepository;

import javax.persistence.EntityNotFoundException;
import java.awt.*;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class ColorMapService {

    @Autowired
    private ColorMapRepository colorMapRepository;

    public ColorMap getRandomColor(){
        List<ColorMap> allColors = colorMapRepository.findAll();
        return allColors.get(new Random().nextInt(allColors.size()));
    }

    public ColorMapDto save(ColorMapDto dto){
        ColorMap color = updateColorMap(dto);
        return ColorMapDto.fromEntity(colorMapRepository.save(color));
    }

    private ColorMap updateColorMap(ColorMapDto dto) {
        ColorMap colorMap = new ColorMap();
        if(dto.getId()!=null){
            colorMap = getColorMapById(dto.getId());
        }
        colorMap.setColorName(Colors.ColorType.valueOf(dto.getColorName()));
        colorMap.setColorMap(dto.getColorMap().getBytes());
        return colorMap;
    }

    public ColorMap getColorMapById(Long id){
        return colorMapRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public List<ColorMap> getAll(){
        return colorMapRepository.findAll();
    }
}
