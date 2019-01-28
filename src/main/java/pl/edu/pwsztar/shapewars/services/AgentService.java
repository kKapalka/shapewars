package pl.edu.pwsztar.shapewars.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.pwsztar.shapewars.entities.Agent;
import pl.edu.pwsztar.shapewars.entities.AgentLearningSet;
import pl.edu.pwsztar.shapewars.entities.LearningSetTurnLog;
import pl.edu.pwsztar.shapewars.entities.User;
import pl.edu.pwsztar.shapewars.entities.dto.AgentDto;
import pl.edu.pwsztar.shapewars.entities.dto.CompleteFightDataDto;
import pl.edu.pwsztar.shapewars.entities.dto.FighterCombatDto;
import pl.edu.pwsztar.shapewars.repositories.AgentLearningSetRepository;
import pl.edu.pwsztar.shapewars.repositories.AgentRepository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Random;

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
        learningSet.setFight(fightService.findFightById(completeFightDataDto.getId()));
        learningSet.setOverallBalancePriority(agent.getOverallBalancePriority());
        learningSet.setEnemyInternalBalancePriority(agent.getEnemyInternalBalancePriority());
        learningSet.setAllyInternalBalancePriority(agent.getAllyInternalBalancePriority());
        learningSet.setIndividualEnemyPriority(agent.getIndividualEnemyPriority());
        learningSet.setIndividualAllyPriority(agent.getIndividualAllyPriority());
        learningSet.setDamageOutputPriority(agent.getDamageOutputPriority());
        return agentLearningSetRepository.save(learningSet);
    }

    public void updateAgentLearningSet(CompleteFightDataDto completeFightDataDto){
        Optional<AgentLearningSet> optionalAgentLearningSet = agentLearningSetRepository.findByFightId(completeFightDataDto.getId());
        AgentLearningSet learningSet = optionalAgentLearningSet.orElseGet(() -> saveAgentLearningSet(completeFightDataDto));
        List<LearningSetTurnLog> turnLogs = learningSet.getLearningSetTurnLogList();
        turnLogs.add(createTurnLog(completeFightDataDto,learningSet));
        learningSet.setLearningSetTurnLogList(turnLogs);
        agentLearningSetRepository.save(learningSet);
    }

    public void onBattleFinishForUser(String username){

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

    }
    private Long computeScoresForFighterDtoList(List<FighterCombatDto> fighterCombatDtoList){
        return 0L;
    }

    private User extractPlayerFromDto(CompleteFightDataDto dto){
        return dto.getPlayers()
                .stream().map(playerCombatDto -> userService.getUserByLogin(playerCombatDto.getLogin()))
                .filter(user->user.getEmail()!=null).findFirst().get();
    }
    private User extractBotFromDto(CompleteFightDataDto dto){
        return dto.getPlayers()
                .stream().map(playerCombatDto -> userService.getUserByLogin(playerCombatDto.getLogin()))
                .filter(user->user.getEmail()==null).findFirst().get();
    }
}
