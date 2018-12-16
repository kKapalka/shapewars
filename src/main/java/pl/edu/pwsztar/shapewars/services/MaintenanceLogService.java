package pl.edu.pwsztar.shapewars.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.pwsztar.shapewars.entities.MaintenanceLog;
import pl.edu.pwsztar.shapewars.entities.dto.MaintenanceLogDto;
import pl.edu.pwsztar.shapewars.entities.enums.MessageType;
import pl.edu.pwsztar.shapewars.repositories.MaintenanceLogRepository;
import pl.edu.pwsztar.shapewars.repositories.UserRepository;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MaintenanceLogService {

    @Autowired
    private MaintenanceLogRepository maintenanceLogRepository;

    @Autowired
    private UserRepository userRepository;

    public MaintenanceLogDto save(MaintenanceLogDto dto){
        MaintenanceLog maintenanceLog = updateAction(dto);
        return MaintenanceLogDto.fromEntity(maintenanceLogRepository.save(maintenanceLog));
    }

    private MaintenanceLog updateAction(MaintenanceLogDto dto){
        MaintenanceLog maintenanceLog = new MaintenanceLog();
        if(dto.getId()!=null){
            maintenanceLog=maintenanceLogRepository.getOne(dto.getId());
        }
        maintenanceLog.setInformer(userRepository.findByLogin
                (dto.getInformerName()).orElseThrow(EntityNotFoundException::new));
        maintenanceLog.setMessageTime(LocalDateTime.now());
        maintenanceLog.setMessage(dto.getMessage());
        maintenanceLog.setMessageType(MessageType.valueOf(dto.getMessageType()));
        return maintenanceLog;
    }

    private List<String> getMessageTypes(){
        return Arrays.stream(MessageType.values()).map(Enum::name).collect(Collectors.toList());
    }
}

