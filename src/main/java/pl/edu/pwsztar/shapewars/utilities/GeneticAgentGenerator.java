package pl.edu.pwsztar.shapewars.utilities;

import static pl.edu.pwsztar.shapewars.utilities.StaticValues.LEARNING_SCORE_WEIGHT;
import static pl.edu.pwsztar.shapewars.utilities.StaticValues.LEARNING_TURN_COUNT_WEIGHT;
import static pl.edu.pwsztar.shapewars.utilities.StaticValues.LEARNING_VICTORY_WEIGHT;

import pl.edu.pwsztar.shapewars.entities.Agent;
import pl.edu.pwsztar.shapewars.entities.AgentLearningSet;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;

public class GeneticAgentGenerator {

    /**
     * Metoda ma za zadanie dokonać ewolucji agenta
     * Obliczane są punkty dla zestawów uczących,
     * potem zestawy są sortowane od najlepiej przystosowanego do najgorzej
     * potem następuje krzyżówka agentów + drobna mutacja
     * @param agent agent
     * @param learningSets lista 5 ostatnich zestawów uczących
     * @return agent-potomek
     */
    public static Agent replaceAgentWithOffspring(Agent agent, List<AgentLearningSet> learningSets){
        Map<AgentLearningSet,Long> learningSetScore = createMapOfSetsWithScores(learningSets);
        learningSets.sort((set1,set2)->learningSetScore.get(set2).intValue()-learningSetScore.get(set1).intValue());
        return useSortedLearningSetToDetermineOffpringParameters(agent,learningSets);
    }

    /**
     * Metoda dokonuje punktacji zachowań podczas zestawów uczących, na podstawie których będą one sortowane
     * Pożądane zachowania: Wygranie walki (+20),
     * przetrwanie rundy (+2/runda)
     * jak najwyższa różnica między punktami agenta a gracza na korzyść agenta (1/30pkt różnicy)
     * @param learningSets zestawy uczące
     * @return mapa [zestaw uczący,punkty]
     */
    private static Map<AgentLearningSet,Long> createMapOfSetsWithScores(List<AgentLearningSet> learningSets){
        Map<AgentLearningSet,Long> learningSetScore = new HashMap<>();
        learningSets.forEach(learningSet -> {
            AtomicReference<Double> bonus= new AtomicReference<>(0d);
            learningSet.getLearningSetTurnLogList().forEach(turnLog -> bonus.updateAndGet(v -> v + (double) (turnLog.getAllyScore() - turnLog.getEnemyScore()) *LEARNING_SCORE_WEIGHT));
            int size = learningSet.getLearningSetTurnLogList().size();
            learningSetScore.put(learningSet, Math.round(bonus.get())+
                    (long) (size*LEARNING_TURN_COUNT_WEIGHT) + ((learningSet.getLearningSetTurnLogList().get(size-1).getAllyScore()==50L &&
                    learningSet.getLearningSetTurnLogList().get(size-1).getEnemyScore()==-50L)?LEARNING_VICTORY_WEIGHT:
                                                                0L));

        });
        return learningSetScore;
    }

    /**
     * Metoda ustawia nowe parametry agenta na podstawie średniej ważonej zestawów uczących
     * oraz dokonuje mutacji
     * @param agent agent do aktualizacji
     * @param learningSets zestawy uczące, razem z ich parametrami
     * @return agent-potomek
     */
    private static Agent useSortedLearningSetToDetermineOffpringParameters(Agent agent,List<AgentLearningSet> learningSets){
        int size=learningSets.size();
        int weightedAverageDenominator=0;
        for(int i=1;i<=size+1;i++){
            weightedAverageDenominator+=weightedAverageDenominator+i;
        }
        Double newOverallBalancePriority=0d;
        Double newEnemyInternalBalancePriority=0d;
        Double newAllyInternalBalancePriority=0d;
        Double newIndividualEnemyPriority=0d;
        Double newIndividualAllyPriority=0d;
        Double newDamageOutputPriority=0d;
        Random random = new Random();
        random.setSeed(Instant.now().toEpochMilli());
       for(int i=0;i<size;i++){
           newOverallBalancePriority+=learningSets.get(i).getOverallBalancePriority()*(size+1-i);
           newEnemyInternalBalancePriority+=learningSets.get(i).getEnemyInternalBalancePriority()*(size+1-i);
           newAllyInternalBalancePriority+=learningSets.get(i).getAllyInternalBalancePriority()*(size+1-i);
           newIndividualEnemyPriority+=learningSets.get(i).getIndividualEnemyPriority()*(size+1-i);
           newIndividualAllyPriority+=learningSets.get(i).getIndividualAllyPriority()*(size+1-i);
           newDamageOutputPriority+=learningSets.get(i).getDamageOutputPriority()*(size+1-i);
       }
        newOverallBalancePriority+=random.nextDouble();
        newEnemyInternalBalancePriority+=random.nextDouble();
        newAllyInternalBalancePriority+=random.nextDouble();
        newIndividualEnemyPriority+=random.nextDouble();
        newIndividualAllyPriority+=random.nextDouble();
        newDamageOutputPriority+=random.nextDouble();

        agent.setEnemyInternalBalancePriority(newEnemyInternalBalancePriority/(double)weightedAverageDenominator);
        agent.setAllyInternalBalancePriority(newAllyInternalBalancePriority/(double)weightedAverageDenominator);
        agent.setIndividualAllyPriority(newIndividualEnemyPriority/(double)weightedAverageDenominator);
        agent.setIndividualEnemyPriority(newIndividualAllyPriority/(double)weightedAverageDenominator);
        agent.setDamageOutputPriority(newDamageOutputPriority/(double)weightedAverageDenominator);
        agent.setOverallBalancePriority(newOverallBalancePriority/(double)weightedAverageDenominator);
        return agent;
    }
}
