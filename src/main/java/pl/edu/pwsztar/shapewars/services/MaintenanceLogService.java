package pl.edu.pwsztar.shapewars.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.pwsztar.shapewars.entities.MaintenanceLog;
import pl.edu.pwsztar.shapewars.entities.User;
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

    public MaintenanceLogDto save(MaintenanceLogDto dto) throws Exception {
        MaintenanceLog maintenanceLog = updateMaintenanceLog(dto);
        return MaintenanceLogDto.fromEntity(maintenanceLogRepository.save(maintenanceLog));
    }

    private MaintenanceLog updateMaintenanceLog(MaintenanceLogDto dto) throws Exception {
        User user = userRepository.findByLoginEquals
                (dto.getInformerName()).orElseThrow(EntityNotFoundException::new);
        if(!user.isAdmin()){
            throw new Exception("You've got no access to add entries to this table");
        }
        MaintenanceLog maintenanceLog = new MaintenanceLog();
        if(dto.getId()!=null){
            maintenanceLog=maintenanceLogRepository.getOne(dto.getId());
        }
        maintenanceLog.setInformer(user);
        maintenanceLog.setMessageTime(LocalDateTime.now());
        maintenanceLog.setMessage(dto.getMessage());
        maintenanceLog.setMessageType(MessageType.valueOf(dto.getMessageType()));
        return maintenanceLog;
    }

    public List<String> getMessageTypes(){
        return Arrays.stream(MessageType.values()).map(Enum::name).collect(Collectors.toList());
    }

    public MaintenanceLogDto retrieveLatest(){
        return MaintenanceLogDto.fromEntity(maintenanceLogRepository.findFirstByOrderByMessageTimeDesc());
    }
}

