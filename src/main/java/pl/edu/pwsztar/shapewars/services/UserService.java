package pl.edu.pwsztar.shapewars.services;

import static pl.edu.pwsztar.shapewars.utilities.StaticValues.BASIC_EXPERIENCE_VALUE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.pwsztar.shapewars.entities.Fight;
import pl.edu.pwsztar.shapewars.entities.Fighter;
import pl.edu.pwsztar.shapewars.entities.Shape;
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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
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
        User bot = new User();
        bot.setLogin("BOT_LEVEL" + level +"_"+LocalDateTime.now().toString());
        bot.setLevel(level);
        bot.setExperiencePoints(0L);
        userRepository.save(bot);
        bot.setFighterList(Arrays.asList(fighterService.generateFighter(bot,FighterSlot.SLOT_1),
              fighterService.generateFighter(bot,FighterSlot.SLOT_2),
                fighterService.generateFighter(bot,FighterSlot.SLOT_3),fighterService.generateFighter(bot,
                    FighterSlot.SLOT_4)));
        return bot;
    }
    public void delete(User user)
    {

        userRepository.delete(user);
    }

    public List<User> getFriendsByLogin(String login){

        List<String> friends=new ArrayList<>();
        messageService.getAllMessagesByUserName(login).stream().map(MessageDto::getMessagePlayers)
                .forEach(friends::addAll);
        friends=friends.stream().distinct().collect(Collectors.toList());
        friends.remove(login);
         return userRepository.findAllByLoginIn(friends);
    }
    public List<User> getAll(){
        return userRepository.findAll();
    }

    public void applyLevelChangesToUsers(User winner, User loser){

        winner.setExperiencePoints(winner.getExperiencePoints() + (BASIC_EXPERIENCE_VALUE + (loser.getLevel() * BASIC_EXPERIENCE_VALUE)));
        loser.setExperiencePoints(loser.getExperiencePoints() + Math.round((float)(BASIC_EXPERIENCE_VALUE + (winner.getLevel() * BASIC_EXPERIENCE_VALUE))/2f));
        Long threshold = experienceThresholdService.getByLevel(winner.getLevel()).getThreshold();
        if(winner.getExperiencePoints()>threshold){
            System.out.println("trigger");
            System.out.println(winner.getExperiencePoints());
            System.out.println(threshold);
            winner.setExperiencePoints(winner.getExperiencePoints()-threshold);
            winner.setLevel(winner.getLevel()+1L);
        }
        threshold = experienceThresholdService.getByLevel(loser.getLevel()).getThreshold();
        if(loser.getExperiencePoints()>threshold){
            System.out.println("trigger");
            System.out.println(loser.getExperiencePoints());
            System.out.println(threshold);
            loser.setExperiencePoints(loser.getExperiencePoints()-threshold);
            loser.setLevel(loser.getLevel()+1L);
        }
    }
    public List<User> findAllBots(){
        return userRepository.findAllByEmail(null);
    }

    public List<User> findPlayersByLogins(List<String> logins){
        return userRepository.findAllByLoginIn(logins);
    }
    public void deleteBot(User bot){
        bot.setFighterList(null);
        User savedBot = userRepository.save(bot);
        userRepository.delete(savedBot);
    }
    public void saveUser(User user){
        userRepository.save(user);
    }
}
