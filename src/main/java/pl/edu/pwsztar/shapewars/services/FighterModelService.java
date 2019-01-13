package pl.edu.pwsztar.shapewars.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.pwsztar.shapewars.entities.ColorMap;
import pl.edu.pwsztar.shapewars.entities.FighterModelReference;
import pl.edu.pwsztar.shapewars.entities.Shape;
import pl.edu.pwsztar.shapewars.repositories.FighterModelReferenceRepository;
import pl.edu.pwsztar.shapewars.utilities.FighterImageGenerator;

import java.util.List;
import java.util.Optional;

@Service
public class FighterModelService {

    @Autowired
    private FighterModelReferenceRepository repository;

    @Autowired
    private ShapeService shapeService;

    @Autowired
    private ColorMapService colorMapService;

    public FighterModelReference getRandomReference(){
        Shape shape = shapeService.getRandomShape();
        ColorMap colorMap = colorMapService.getRandomColor();
        Optional<FighterModelReference> reference = repository.findByShapeAndColor(shape,colorMap);
        if(!reference.isPresent()){
            FighterModelReference newReference = new FighterModelReference();
            newReference.setColor(colorMap);
            newReference.setShape(shape);
            newReference.setFighterImage(FighterImageGenerator.generateImageFrom(shape,colorMap));
            return repository.save(newReference);
        } else{
            return reference.get();
        }
    }

    public void refreshModelsViaShape(Shape shape){
        List<FighterModelReference> referenceList = repository.findAllByShape(shape);
        referenceList.forEach(ref->ref.setFighterImage(FighterImageGenerator.generateImageFrom(shape,ref.getColor())));
        repository.saveAll(referenceList);
    }
    public void refreshModelsViaColorMap(ColorMap colorMap){
        List<FighterModelReference> referenceList = repository.findAllByColor(colorMap);
        referenceList.forEach(ref->ref.setFighterImage(FighterImageGenerator.generateImageFrom(ref.getShape(),colorMap)));
        repository.saveAll(referenceList);
    }
}
