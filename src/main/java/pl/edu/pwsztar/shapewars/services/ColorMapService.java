package pl.edu.pwsztar.shapewars.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.edu.pwsztar.shapewars.entities.Changelog;
import pl.edu.pwsztar.shapewars.entities.ColorMap;
import pl.edu.pwsztar.shapewars.entities.Fighter;
import pl.edu.pwsztar.shapewars.entities.dto.ColorMapDto;
import pl.edu.pwsztar.shapewars.entities.dto.FighterDto;
import pl.edu.pwsztar.shapewars.repositories.ChangelogRepository;
import pl.edu.pwsztar.shapewars.repositories.ColorMapRepository;
import pl.edu.pwsztar.shapewars.utilities.ChangelogUtility;

import javax.persistence.EntityNotFoundException;
import java.awt.*;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class ColorMapService {

    @Autowired
    private ColorMapRepository colorMapRepository;

    @Autowired
    private FighterService fighterService;

    @Autowired
    private ChangelogRepository changelogRepository;

    public ColorMap getRandomColor(){
        List<ColorMap> allColors = colorMapRepository.findAll();
        return allColors.get(new Random().nextInt(allColors.size()));
    }

    public ColorMapDto save(ColorMapDto dto){
        Changelog changelog =
              ChangelogUtility.compute(colorMapRepository.findById(dto.getId()).orElse(new ColorMap()),dto);
        ColorMap color = updateColorMap(dto);
        ColorMap newColor = colorMapRepository.save(color);
        changelogRepository.save(changelog);
        fighterService.refreshFightersViaColorMap(newColor);

        return ColorMapDto.fromEntity(newColor);
    }

    private ColorMap updateColorMap(ColorMapDto dto) {
        ColorMap colorMap = new ColorMap();
        if(dto.getId()!=null){
            colorMap = getColorMapById(dto.getId());
        }
        colorMap.setColorName(dto.getColorName());
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
