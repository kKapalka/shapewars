package pl.edu.pwsztar.shapewars.services;

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
        userRepository.save(bot);
        bot.setFighterList(Arrays.asList(fighterService.generateFighter(bot,FighterSlot.SLOT_1),
              fighterService.generateFighter(bot,FighterSlot.SLOT_2),
                fighterService.generateFighter(bot,FighterSlot.SLOT_3),fighterService.generateFighter(bot,
                    FighterSlot.SLOT_4)));
        return bot;
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
        int winnerStoredLevel = winner.getLevel().intValue();
        int loserStoredLevel = loser.getLevel().intValue();
        if(winner.getEmail()!=null) {
            winner.setExperiencePoints(winner.getExperiencePoints() + (loserStoredLevel * 70));
            winner.getFighterList().forEach(fighter -> {
                if(fighter.getSlot() != FighterSlot.INVENTORY) {
                    fighter.setExperiencePoints(Math.round(fighter.getExperiencePoints() + (loserStoredLevel * 70) *
                                                                                           Math.pow(1.15, (winnerStoredLevel -
                                                                                                           fighter.getLevel()))));
                    Long fighterThreshold = experienceThresholdService.getByLevel(fighter.getLevel()).getThreshold();
                    if(fighter.getExperiencePoints() > fighterThreshold) {
                        fighter = levelUp(fighter, fighterThreshold);
                    }
                    fighterService.saveFighter(fighter);
                }
            });
        }
        if(loser.getEmail()!=null) {
            loser.setExperiencePoints(loser.getExperiencePoints() + (winnerStoredLevel * 35));
            loser.getFighterList().forEach(fighter -> {
                if(fighter.getSlot() != FighterSlot.INVENTORY) {
                    fighter.setExperiencePoints(Math.round(fighter.getExperiencePoints() + (winnerStoredLevel * 35) *
                                                                                           Math.pow(1.15, (loserStoredLevel -
                                                                                                           fighter.getLevel()))));
                    Long fighterThreshold = experienceThresholdService.getByLevel(fighter.getLevel()).getThreshold();
                    if(fighter.getExperiencePoints() > fighterThreshold) {
                        fighter = levelUp(fighter, fighterThreshold);
                    }
                    fighterService.saveFighter(fighter);
                }
            });
        }
        if(winner.getEmail()!=null) {
            Long winnerXPThreshold = experienceThresholdService.getByLevel(winner.getLevel()).getThreshold();
            if(winner.getExperiencePoints() > winnerXPThreshold) {
                winner.setExperiencePoints(winner.getExperiencePoints() - winnerXPThreshold);
                winner.setLevel(winner.getLevel() + 1L);
            }
        }
        List<Fighter> loot;
        if(loser.getEmail()==null){
            //AI loser can lose shapes from its party - it will be deleted afterwards, so no worry
            loot = loser.getFighterList();
        } else{
            Long loserXPThreshold = experienceThresholdService.getByLevel(loser.getLevel()).getThreshold();
            if(loser.getExperiencePoints()>loserXPThreshold){
                loser.setExperiencePoints(loser.getExperiencePoints()-loserXPThreshold);
                loser.setLevel(loser.getLevel()+1L);
            }
            loot = loser.getFighterList().stream().filter(fighter->fighter.getSlot()==FighterSlot.INVENTORY).collect(Collectors.toList());
        }
        //if loser has shapes to lose
        //at least one
        if(loot.size()!=0){
            loot.get(new Random().nextInt(loot.size())).setOwner(winner);
            //at most 2 will be transferred to winner
            if(loot.size()>2){
                loot.get(new Random().nextInt(loot.size())).setOwner(winner);
            }
        }
        userRepository.save(winner);
        userRepository.save(loser);
        //bots won't have their e-mails set
        if(fight.getPlayerTwo().getEmail()==null){
            //it means it really is an AI
            fight.getPlayerTwo().getFighterList().forEach(fighter->{
                if(fighter.getOwner()==fight.getPlayerTwo()){
                    fighter.setOwner(null);
                }
            });
            delete(fight.getPlayerTwo());
        }
    }

    public List<User> getFriendsByLogin(String login){
        Long userId = userRepository.findByLoginEquals(login).orElseThrow(EntityNotFoundException::new).getID();
        List<String> friends= messageService.getAllMessagesByUserId(userId)
                .stream().map(MessageDto::getReceiver).filter(frLogin-> !frLogin.equals(login)).collect(Collectors.toList());
        friends.addAll(messageService.getAllMessagesByUserId(userId)
                .stream().map(MessageDto::getSender).filter(frLogin-> !frLogin.equals(login)).collect(Collectors.toList()));
        return userRepository.findAllByLoginIn(friends);
    }
    public List<User> getAll(){
        return userRepository.findAll();
    }

    private Fighter levelUp(Fighter fighter, Long fighterThreshold){
        fighter.setExperiencePoints(fighter.getExperiencePoints()-fighterThreshold);
        fighter.setLevel(fighter.getLevel()+1);
        Shape shape = fighter.getFighterModelReferrence().getShape();
        fighter.setStrengthModifier(fighter.getStrengthModifier()+
                (long)(new Random().nextInt((shape.getSTRMaxGrowth().intValue()-shape.getSTRMinGrowth().intValue())
                        +shape.getSTRMinGrowth().intValue())));
        fighter.setHitPointsModifier(fighter.getHitPointsModifier()+
                (long)(new Random().nextInt((shape.getHPMaxGrowth().intValue()-shape.getHPMinGrowth().intValue())
                        +shape.getHPMinGrowth().intValue())));
        fighter.setArmorModifier(fighter.getArmorModifier()+
                (long)(new Random().nextInt((shape.getARMMaxGrowth().intValue()-shape.getARMMinGrowth().intValue())
                        +shape.getARMMinGrowth().intValue())));
        return fighter;
    }
}
