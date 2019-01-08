package pl.edu.pwsztar.shapewars.controllers.rest;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pl.edu.pwsztar.shapewars.entities.dto.ChangelogDto;
import pl.edu.pwsztar.shapewars.services.ChangelogService;

@RestController
@RequestMapping("changelog")
@CrossOrigin
public class ChangelogController {

    @Autowired
    private ChangelogService changelogService;

    @GetMapping("all")
    public List<ChangelogDto> findAll(){
        return changelogService.getAll();
    }
}
