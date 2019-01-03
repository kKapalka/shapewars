package pl.edu.pwsztar.shapewars.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.pwsztar.shapewars.entities.Fight;
import pl.edu.pwsztar.shapewars.entities.User;
import pl.edu.pwsztar.shapewars.entities.dto.MessageDto;
import pl.edu.pwsztar.shapewars.entities.dto.UserDto;
import pl.edu.pwsztar.shapewars.entities.enums.FightStatus;
import pl.edu.pwsztar.shapewars.entities.enums.FighterSlot;
import pl.edu.pwsztar.shapewars.exceptions.EmailExistsException;
import pl.edu.pwsztar.shapewars.repositories.UserRepository;
import pl.edu.pwsztar.shapewars.services.interfaces.IUserService;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import javax.websocket.Session;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FighterService fighterService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private ExperienceThresholdService experienceThresholdService;

    @Transactional
    @Override
    public User registerNewUserAccount(UserDto accountDto)
            throws EmailExistsException {

        if (emailExist(accountDto.getEmail())) {
            throw new EmailExistsException(
                    "There is an account with that email address:"  + accountDto.getEmail());
        }
        User user = new User();
        user.setLogin(accountDto.getLogin());
        user.setPassword(accountDto.getPassword());
        user.setEmail(accountDto.getEmail());
        user.setLevel(1L);
        user.setExperiencePoints(0L);

        return userRepository.save(user);
    }
    private boolean emailExist(String email) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            return true;
        }
        return false;
    }
    public User getUserById(Long id){
        return userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public User addFightersToUser(Long userId, List<Long> list){
        User user = getUserById(userId);
        user.setFighterList(list.stream().map(fighterService::getFighterById).collect(Collectors.toList()));
        return userRepository.save(user);
    }

    public User getUserByLogin(String login){
        return userRepository.findByLoginEquals(login).orElseThrow(EntityNotFoundException::new);
    }

    public User generateOpponentWithLevel(Long level){
        User user = new User();
        user.setLevel(level);
        userRepository.save(user);
        user.setFighterList(Arrays.asList(fighterService.generateFighter(user),fighterService.generateFighter(user),
                fighterService.generateFighter(user),fighterService.generateFighter(user)));
        return user;
    }
    public void delete(User user){
        userRepository.delete(user);
    }
    public void processFightFinalization(Fight fight){
        User winner, loser;
        if(fight.getFightStatus()== FightStatus.VICTORY_PLAYER_ONE){
            winner=fight.getPlayerOne();
            loser=fight.getPlayerTwo();
        } else{
            winner=fight.getPlayerTwo();
            loser=fight.getPlayerOne();
        }

        winner.setExperiencePoints(winner.getExperiencePoints()+(winner.getLevel()*70));
        winner.getFighterList().forEach(fighter->{
            if(fighter.getSlot()!= FighterSlot.INVENTORY) {
                fighter.setExperiencePoints(Math.round(fighter.getExperiencePoints() +
                        (loser.getLevel() * 70) * Math.pow(1.15, (winner.getLevel() - fighter.getLevel()))));
                Long fighterThreshold = experienceThresholdService.getByLevel(fighter.getLevel()).getThreshold();
                if(fighter.getExperiencePoints()>fighterThreshold){
                    fighter.setExperiencePoints(fighter.getExperiencePoints()-fighterThreshold);
                    fighter.setLevel(fighter.getLevel()+1);
                }
                fighterService.saveFighter(fighter);
            }
        });
        loser.setExperiencePoints(loser.getExperiencePoints()+(winner.getLevel()*35));
        loser.getFighterList().forEach(fighter->{
            if(fighter.getSlot()!= FighterSlot.INVENTORY) {
                fighter.setExperiencePoints(Math.round(fighter.getExperiencePoints() +
                        (winner.getLevel() * 35) * Math.pow(1.15, (loser.getLevel() - fighter.getLevel()))));
                Long fighterThreshold = experienceThresholdService.getByLevel(fighter.getLevel()).getThreshold();
                if(fighter.getExperiencePoints()>fighterThreshold){
                    fighter.setExperiencePoints(fighter.getExperiencePoints()-fighterThreshold);
                    fighter.setLevel(fighter.getLevel()+1);
                }
                fighterService.saveFighter(fighter);
            }
        });
        Long winnerXPThreshold = experienceThresholdService.getByLevel(winner.getLevel()).getThreshold();
        if(winner.getExperiencePoints()>winnerXPThreshold){
            winner.setExperiencePoints(winner.getExperiencePoints()-winnerXPThreshold);
            winner.setLevel(winner.getLevel()+1L);
        }
        Long loserXPThreshold = experienceThresholdService.getByLevel(loser.getLevel()).getThreshold();
        if(loser.getExperiencePoints()>loserXPThreshold){
            loser.setExperiencePoints(loser.getExperiencePoints()-loserXPThreshold);
            loser.setLevel(loser.getLevel()+1L);
        }


        if(fight.getPlayerTwo().getLogin()==null){
            //it means it really is an AI
            delete(fight.getPlayerTwo());
        }
    }

    public List<User> getFriendsByLogin(String login){
        Long userId = userRepository.findByLoginEquals(login).orElseThrow(EntityNotFoundException::new).getID();
        List<Long> friendsIds= messageService.getAllMessagesByUserId(userId)
                .stream().map(MessageDto::getReceiverId).filter(id-> !id.equals(userId)).collect(Collectors.toList());
        friendsIds.addAll(messageService.getAllMessagesByUserId(userId)
                .stream().map(MessageDto::getSenderId).filter(id-> !id.equals(userId)).collect(Collectors.toList()));
        return userRepository.findAllById(friendsIds);
    }

}
