package pl.edu.pwsztar.shapewars.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.edu.pwsztar.shapewars.entities.dto.MaintenanceLogDto;
import pl.edu.pwsztar.shapewars.services.MaintenanceLogService;

@RequestMapping("maintenance-log")
@RestController
@CrossOrigin
public class MaintenanceLogController {

    @Autowired
    private MaintenanceLogService maintenanceLogService;

    @PostMapping("save")
    public MaintenanceLogDto save(@RequestBody MaintenanceLogDto dto) throws Exception {
        return maintenanceLogService.save(dto);
    }
    @GetMapping("retrieve")
    public MaintenanceLogDto getLatest(){
        return maintenanceLogService.retrieveLatest();
    }
}
