package pl.edu.pwsztar.shapewars.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pl.edu.pwsztar.shapewars.entities.Agent;
import pl.edu.pwsztar.shapewars.entities.AgentLearningSet;
import pl.edu.pwsztar.shapewars.entities.LearningSetTurnLog;
import pl.edu.pwsztar.shapewars.entities.User;
import pl.edu.pwsztar.shapewars.entities.dto.AgentDto;
import pl.edu.pwsztar.shapewars.entities.dto.CompleteFightDataDto;
import pl.edu.pwsztar.shapewars.entities.dto.FightDto;
import pl.edu.pwsztar.shapewars.entities.dto.FighterCombatDto;
import pl.edu.pwsztar.shapewars.repositories.AgentLearningSetRepository;
import pl.edu.pwsztar.shapewars.repositories.AgentRepository;
import pl.edu.pwsztar.shapewars.utilities.GeneticAgentGenerator;

import javax.persistence.EntityNotFoundException;
import java.time.Instant;
import java.util.*;

@Service
public class AgentService {

    @Autowired
    private AgentRepository agentRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private FightService fightService;

    @Autowired
    private AgentLearningSetRepository agentLearningSetRepository;

    public AgentDto getAgentByPlayerName(String username){
        Random random = new Random();
        random.setSeed(Instant.now().toEpochMilli());
        Optional<Agent> agent = agentRepository.findByUsername(username);
        if(agent.isPresent()){
            return AgentDto.fromEntity(agent.get());
        } else{
            Agent newAgent = new Agent();
            newAgent.setDedicatedPlayer(userService.getUserByLogin(username));
            newAgent.setOverallBalancePriority(random.nextDouble());
            newAgent.setEnemyInternalBalancePriority(random.nextDouble());
            newAgent.setAllyInternalBalancePriority(random.nextDouble());
            newAgent.setIndividualEnemyPriority(random.nextDouble());
            newAgent.setIndividualAllyPriority(random.nextDouble());
            newAgent.setDamageOutputPriority(random.nextDouble());
            return AgentDto.fromEntity(agentRepository.save(newAgent));
        }
    }

    public AgentLearningSet saveAgentLearningSet(CompleteFightDataDto completeFightDataDto){
        AgentLearningSet learningSet = new AgentLearningSet();
        Agent agent = agentRepository.findByUsername(extractPlayerFromDto(completeFightDataDto).getLogin()).get();
        learningSet.setAgent(agent);
        learningSet.setAgentVersion(agent.getVersion());
        learningSet.setFight(fightService.findFightById(completeFightDataDto.getId()));
        learningSet.setOverallBalancePriority(agent.getOverallBalancePriority());
        learningSet.setEnemyInternalBalancePriority(agent.getEnemyInternalBalancePriority());
        learningSet.setAllyInternalBalancePriority(agent.getAllyInternalBalancePriority());
        learningSet.setIndividualEnemyPriority(agent.getIndividualEnemyPriority());
        learningSet.setIndividualAllyPriority(agent.getIndividualAllyPriority());
        learningSet.setDamageOutputPriority(agent.getDamageOutputPriority());
        return agentLearningSetRepository.save(learningSet);
    }

    public void updateAgentLearningSet(CompleteFightDataDto completeFightDataDto, int turn){
        Optional<AgentLearningSet> optionalAgentLearningSet = agentLearningSetRepository.findByFightId(completeFightDataDto.getId());
        AgentLearningSet learningSet = optionalAgentLearningSet.orElseGet(() -> saveAgentLearningSet(completeFightDataDto));
        List<LearningSetTurnLog> turnLogs = learningSet.getLearningSetTurnLogList();
        if(turnLogs==null){
            turnLogs = new ArrayList<>();
        }
        if(turn>turnLogs.size()){
            turnLogs.add(createTurnLog(completeFightDataDto,learningSet));
        }
        learningSet.setLearningSetTurnLogList(turnLogs);
        agentLearningSetRepository.save(learningSet);
    }

    public void onBattleFinish(FightDto fightDto){
        AgentLearningSet agentLearningSet = agentLearningSetRepository.findByFightId(fightDto.getId()).orElseThrow(EntityNotFoundException::new);
        agentLearningSetRepository.save(agentLearningSet);
        agentLearningSetRepository.flush();

        User player = extractPlayerFromDto(fightDto);
        Agent agent = agentRepository.findByUsername(player.getLogin())
                .orElseThrow(EntityNotFoundException::new);
        createFinalLearningSetTurnLog(fightDto,player);
        List<AgentLearningSet> agentLearningSets = agentLearningSetRepository.findByAgentOrderByIDAsc(agent, PageRequest.of(0,5));
        agentRepository.save(GeneticAgentGenerator.replaceAgentWithOffspring(agent,agentLearningSets));
    }
    private LearningSetTurnLog createTurnLog(CompleteFightDataDto completeFightDataDto,AgentLearningSet parent){
        LearningSetTurnLog turnLog = new LearningSetTurnLog();
        turnLog.setAgentLearningSet(parent);
        turnLog.setAllyScore(computeScoresForFighterDtoList
                (completeFightDataDto.getPlayers().stream()
                        .filter(playerCombatDto -> playerCombatDto.getLogin()
                                .equals(extractBotFromDto(completeFightDataDto).getLogin())).findFirst().get().getAllFighterList()));
        turnLog.setEnemyScore(computeScoresForFighterDtoList
                (completeFightDataDto.getPlayers().stream()
                        .filter(playerCombatDto -> playerCombatDto.getLogin()
                                .equals(extractPlayerFromDto(completeFightDataDto).getLogin())).findFirst().get().getAllFighterList()));

        return turnLog;
    }
    private Long computeScoresForFighterDtoList(List<FighterCombatDto> fighterCombatDtoList){
        return Math.round(fighterCombatDtoList.stream()
                .map(fighter->(((double)fighter.getCurrentHp()/(double)fighter.getMaximumHp())*20
                        +((double)fighter.getCurrentMana()/(double)fighter.getMaximumMana())*5)).reduce((a,b)->a+b).get());
    }

    private User extractPlayerFromDto(CompleteFightDataDto dto){
        return dto.getPlayers()
                .stream().map(playerCombatDto -> userService.getUserByLogin(playerCombatDto.getLogin()))
                .filter(user->user.getEmail()!=null).findFirst().get();
    }
    private User extractPlayerFromDto(FightDto dto){
        return dto.getPlayerNames()
                .stream().map(name -> userService.getUserByLogin(name))
                .filter(user->user.getEmail()!=null).findFirst().get();
    }
    private User extractBotFromDto(CompleteFightDataDto dto){
        return dto.getPlayers()
                .stream().map(playerCombatDto -> userService.getUserByLogin(playerCombatDto.getLogin()))
                .filter(user->user.getEmail()==null).findFirst().get();
    }

    private void createFinalLearningSetTurnLog(FightDto dto, User dedicatedPlayer){
        AgentLearningSet finalAgentLearningSet = agentLearningSetRepository.findByFightId(dto.getId())
                .orElseThrow(EntityNotFoundException::new);
        List<LearningSetTurnLog> turnLog = finalAgentLearningSet.getLearningSetTurnLogList();
        LearningSetTurnLog newTurnLog = new LearningSetTurnLog();
        newTurnLog.setAgentLearningSet(finalAgentLearningSet);
        if(!dto.getWinnerName().equals(dedicatedPlayer.getLogin())){
            newTurnLog.setAllyScore(100L);
            newTurnLog.setEnemyScore(-100L);
        } else{
            newTurnLog.setAllyScore(-100L);
            newTurnLog.setEnemyScore(100L);
        }
        turnLog.add(newTurnLog);
        finalAgentLearningSet.setLearningSetTurnLogList(turnLog);
        agentLearningSetRepository.save(finalAgentLearningSet);
        agentLearningSetRepository.flush();
    }
}
