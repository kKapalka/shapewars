package pl.edu.pwsztar.shapewars.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.edu.pwsztar.shapewars.entities.Changelog;
import pl.edu.pwsztar.shapewars.entities.ColorDamage;
import pl.edu.pwsztar.shapewars.entities.ColorMap;
import pl.edu.pwsztar.shapewars.entities.Fighter;
import pl.edu.pwsztar.shapewars.entities.dto.ColorDamageDto;
import pl.edu.pwsztar.shapewars.entities.dto.ColorMapDto;
import pl.edu.pwsztar.shapewars.entities.dto.FighterDto;
import pl.edu.pwsztar.shapewars.repositories.ChangelogRepository;
import pl.edu.pwsztar.shapewars.repositories.ColorDamageRepository;
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
    private ColorDamageRepository colorDamageRepository;

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
              ChangelogUtility.compute(dto.getId()==null?new ColorMap():
                                       colorMapRepository.findById(dto.getId()).orElse(new ColorMap()),dto);
        ColorMap color = updateColorMap(dto);
        ColorMap newColor = colorMapRepository.save(color);
        if(!changelog.getChange().equals("")) {
            changelogRepository.save(changelog);
        }
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
        colorMap.setColorDamageList(dto.getColorDamageDtoList().stream().map(this::updateColorDamage).collect(Collectors.toList()));
        return colorMap;
    }
    private ColorDamage updateColorDamage(ColorDamageDto dto){
        if(dto.getColorName().equals(dto.getEnemyColorName())){
            return null;
        }
        ColorDamage colorDamage = new ColorDamage();
        if(dto.getId()!=null){
            colorDamage = colorDamageRepository.findById(dto.getId()).orElseThrow(EntityNotFoundException::new);
        }
        colorDamage.setColor(getColorMapByName(dto.getColorName()));
        colorDamage.setEnemyColor(getColorMapByName(dto.getEnemyColorName()));
        colorDamage.setDamagePercentage(dto.getDamagePercentage());
        return colorDamage;
    }
    public ColorMap getColorMapById(Long id){
        return colorMapRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }
    public ColorMap getColorMapByName(String name){
        return colorMapRepository.findByColorName(name).orElseThrow(EntityNotFoundException::new);
    }
    public List<ColorMap> getAll(){
        return colorMapRepository.findAll();
    }
}
