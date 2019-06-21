package pl.edu.pwsztar.shapewars.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.edu.pwsztar.shapewars.entities.dto.ShapeDto;
import pl.edu.pwsztar.shapewars.services.ShapeService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("shapes")
@CrossOrigin
public class ShapeController {

    @Autowired
    private ShapeService shapeService;

    /**
     * Zapis kształtu
     * @param dto dto nowego kształtu do zapisania
     * @return dto zapisanego kształtu
     */
    @PostMapping("save")
    public ShapeDto save(@RequestBody ShapeDto dto){
        return shapeService.save(dto);
    }

    /**
     * Pobranie wszystkich kształtów
     * @return lista kształtów
     */
    @GetMapping("all")
    public List<ShapeDto> getAll(){
        return shapeService.getAll().stream().map(ShapeDto::fromEntity).collect(Collectors.toList());
    }

    /**
     * Pobranie kształtu o ustalonym id
     * @param id id kształtu
     * @return kstałt o podanym id
     */
    @GetMapping("{id}")
    public ShapeDto getById(@PathVariable Long id){
        return ShapeDto.fromEntity(shapeService.getShapeById(id));
    }

    /**
     * Pobranie wyglądu pierwszego istniejącego kształtu - metoda używana
     * podczas prezentacji koloru
     * @return ikona kształtu w postaci ciągu znakowego
     */
    @GetMapping("sample")
    public String getSampleShapeIcon(){
        return new String(shapeService.getAll().get(0).getImage());
    }
}
