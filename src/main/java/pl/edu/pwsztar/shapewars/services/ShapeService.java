package pl.edu.pwsztar.shapewars.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.pwsztar.shapewars.entities.Shape;
import pl.edu.pwsztar.shapewars.entities.dto.ShapeDto;
import pl.edu.pwsztar.shapewars.repositories.ShapeRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Random;

@Service
public class ShapeService {

    @Autowired
    private ShapeRepository shapeRepository;

    @Autowired
    private SkillService skillService;

    public ShapeDto save(ShapeDto dto){
        System.out.println(dto);
        Shape shape = updateShape(dto);
        return ShapeDto.fromEntity(shapeRepository.save(shape));
    }

    private Shape updateShape(ShapeDto dto){
        System.out.println(dto);
        Shape shape = new Shape();
        if(dto.getId()!=null){
            shape=shapeRepository.getOne(dto.getId());
        }
        shape.setName(dto.getName());
        shape.setSkillSet(skillService.getSkillsByIdIn(dto.getSkillIDset()));
        shape.setBaselineSpeed(dto.getSpeed());
        shape.setBaselineHp(dto.getHpParameters().get(0));
        shape.setHPMinGrowth(dto.getHpParameters().get(1));
        shape.setHPMaxGrowth(dto.getHpParameters().get(2));
        shape.setBaselineArmor(dto.getArmParameters().get(0));
        shape.setARMMinGrowth(dto.getArmParameters().get(1));
        shape.setARMMaxGrowth(dto.getArmParameters().get(2));
        shape.setBaselineStrength(dto.getStrParameters().get(0));
        shape.setSTRMinGrowth(dto.getStrParameters().get(1));
        shape.setSTRMaxGrowth(dto.getStrParameters().get(2));
        shape.setImage(dto.getImage().getBytes());
        return shape;
    }

    public Shape getShapeById(Long id){
        return shapeRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }
    public Shape getRandomShape(){
        List<Shape> shapeList = shapeRepository.findAll();
        return shapeList.get(new Random().nextInt(shapeList.size()));
    }

    public List<Shape> getAll(){
        return shapeRepository.findAll();
    }

}
