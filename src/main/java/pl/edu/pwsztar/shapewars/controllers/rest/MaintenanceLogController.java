package pl.edu.pwsztar.shapewars.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.edu.pwsztar.shapewars.entities.Message;
import pl.edu.pwsztar.shapewars.entities.dto.MaintenanceLogDto;
import pl.edu.pwsztar.shapewars.entities.enums.MessageType;
import pl.edu.pwsztar.shapewars.services.MaintenanceLogService;

import java.util.Arrays;
import java.util.List;

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
    @GetMapping("message-types")
    public List<MessageType> getMessageTypes(){ return Arrays.asList(MessageType.values());
    }
}
