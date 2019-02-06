import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AgentService {

  agentFighters:any;
  playerFighters:any;
  agentFightersCopy:any;
  playerFightersCopy:any;
  selectedTarget:any;
  currentFighter:any;
  agent:any;
  colorMaps:any;
  constructor() { }

  init(agentFighters:any,playerFighters:any,agent:any,colorMaps:any){
    this.agentFighters=agentFighters;
    this.playerFighters=playerFighters;
    this.agent=agent;
    this.colorMaps=colorMaps;
  }
  selectSkill(agentFighters,playerFighters,currentFighter:any){
    this.agentFightersCopy=JSON.stringify(agentFighters);
    this.playerFightersCopy=JSON.stringify(playerFighters);
    return this.lookAheadAndDecideOnBestMove(currentFighter);
  }
  getSelectedTarget(){
    return this.selectedTarget;
  }

  calculateOverallBalance(agentFighters:any,playerFighters:any):number{
    let agentScore = agentFighters.filter(fighter=>!fighter.statusEffects.dead)
      .map(fighter=>(fighter.currentHp/fighter.maximumHp)*20+(fighter.currentMana/fighter.maximumMana)*5)
      .reduce((a,b)=>a+b);
    let playerScore = playerFighters.filter(fighter=>!fighter.statusEffects.dead)
      .map(fighter=>(fighter.currentHp/fighter.maximumHp)*20+(fighter.currentMana/fighter.maximumMana)*5)
      .reduce((a,b)=>a+b);
    let overallScore = (agentScore-playerScore)*2;
    return overallScore;
  }
  calculateInternalBalance(fighters:any,isAgent:boolean):number{
    let balanceScore = fighters.filter(fighter=>!fighter.statusEffects.dead)
      .map(fighter=>(50+(fighter.statusEffects.strengthBonus.value*3)+(fighter.statusEffects.armorBonus.value)+(Math.min(Math.max(-15,fighter.statusEffects.speedBonus.value),10))-(fighter.statusEffects.stunnedForTurns*20))
        *((fighter.currentHp/fighter.maximumHp)*20+(fighter.currentMana/fighter.maximumMana)*5)/50)
      .reduce((a,b)=>a+b);
    if(!isAgent){
      return 110-balanceScore;
    }
    return balanceScore;
  }
  calculateIndividualScore(fighters:any,isAgent:boolean):number{
    let fighterScores = fighters.filter(fighter=>!fighter.statusEffects.dead)
      .map(fighter=>(50+(fighter.statusEffects.strengthBonus.value*3)+(fighter.statusEffects.armorBonus.value)+(Math.min(Math.max(-15,fighter.statusEffects.speedBonus.value),10)*2)-(fighter.statusEffects.stunnedForTurns*20))
        *((((fighter.currentHp/fighter.maximumHp)*20)+((fighter.currentMana/fighter.maximumMana)*5)))/12.5)
    let individualScore = 0;
    if(!isAgent){
      return 110-Math.min(...fighterScores)
    } else{
      individualScore = Math.max(...fighterScores);
    }
    return individualScore;
  }
  calculateDamageOutputScore(playerFighters:any,currentFighter:any):number{
    let colorDamages = this.colorMaps.find(color=>color.colorName==currentFighter.fighterModelReferenceDto.colorName).colorDamages;
    let multipliers = playerFighters.map(fighter=>(colorDamages[fighter.fighterModelReferenceDto.colorName]===undefined?100:colorDamages[fighter.fighterModelReferenceDto.colorName]))
    let maximumMultiplier = Math.max(...multipliers);
    let damagePotency = (15+(currentFighter.strength+currentFighter.statusEffects.strengthBonus.value))/30;
    let damageScore = maximumMultiplier*damagePotency;
    return damageScore;
  }

  lookAheadAndDecideOnBestMove(currentFighter:any):any[]{
    this.currentFighter=currentFighter;
    let scoreTargetMap = [];
    let validSkillset = currentFighter.skillSet
      .filter(skill=>skill.cost<=currentFighter.currentMana);
    validSkillset.forEach(skill=>scoreTargetMap.push(this.simulateSkillsImpact(skill)));
    this.selectedTarget = scoreTargetMap.find(scoreTarget=>scoreTarget.value===Math.max(...scoreTargetMap.map(st=>st.value))).target;
    console.log(scoreTargetMap);
    return validSkillset[scoreTargetMap.indexOf(scoreTargetMap.find(scoreTarget=>scoreTarget.value===Math.max(...scoreTargetMap.map(st=>st.value))))];
  }
  simulateSkillsImpact(skill:any):any{
    console.log(skill.name);
    let targetScores = [];
    let effects=[].concat.apply([],skill.skillEffectBundles.map(bundle=>bundle.skillEffectDtos.map(dto=>dto.skillStatusEffect)));
    let validTargets=this.getValidTargets(skill);
    if(effects.includes('DEAL_DAMAGE')){
      for(let target of validTargets){
        targetScores.push({
          target:target.id,
          value:this.performSkillWithTarget(skill,target,this.playerFightersCopy,this.agentFightersCopy)
        });
      }
    }
    else{
      let target=validTargets[0];
      targetScores.push({
        target:target.id,
        value:this.performSkillWithTarget(skill,target,this.playerFightersCopy,this.agentFightersCopy)
      });
    }
    console.log(targetScores);
    return targetScores.find(targetScore=>targetScore.value===Math.max(...targetScores.map(targetScore=>targetScore.value)));
  }
  getValidTargets(skill:any):any[]{
    let playerFighters=JSON.parse(this.playerFightersCopy);
    let agentFighters=JSON.parse(this.agentFightersCopy);

    let targetTypes=[].concat.apply([],skill.skillEffectBundles.map(bundle=>bundle.skillEffectDtos.map(dto=>dto.targetType)));
    if(targetTypes.some(type => ['TARGET_ENEMY', 'RANDOM_ENEMY', 'ALL_ENEMY_UNITS', 'ALL_UNITS'].includes(type))){
      return playerFighters.filter(fighter=>!fighter.statusEffects.dead);
    } else{
      return agentFighters.filter(fighter=>!fighter.statusEffects.dead);
    }
    return [];
  }

  performSkillWithTarget(skill:any,target:any,playerFightersCopy:any,agentFightersCopy:any):number{
    let playerFighters = JSON.parse(playerFightersCopy);
    let agentFighters = JSON.parse(agentFightersCopy);
    let caster = agentFighters.find(fighter=>fighter.id===this.currentFighter.id);
    caster.storedHp=caster.currentHp;
    caster.currentMana-=skill.cost;
    skill.skillEffectBundles.forEach(bundle=>{
      bundle.skillEffectDtos.forEach(effect=>{
        let effectTargets = [];
        if(['TARGET_ENEMY','RANDOM_ENEMY','TARGET_ALLY','RANDOM_ALLY'].includes(effect.targetType)){
          effectTargets.push(target);
        } else if(effect.targetType=='THIS_UNIT'){
          effectTargets.push(caster);
        } else if(effect.targetType==='ALL_ALLIED_UNITS'){
          effectTargets = agentFighters;
        } else if(effect.targetType==='ALL_ENEMY_UNITS'){
          effectTargets = playerFighters;
        } else{
          effectTargets = agentFighters;
          effectTargets.concat(...playerFighters);
        }
        effectTargets = effectTargets.filter(fighter=>!fighter.statusEffects.dead);

        let averageValue = (effect.minValue + effect.maxValue)*bundle.accuracy/200;
        effectTargets.forEach(target=>{
          let storedHp = target.storedHp;
          if((effect.skillStatusEffect=='DEAL_DAMAGE' || effect.skillStatusEffect=='RESTORE_HEALTH')) {
            console.log("triggered damage dealing?")
            let HPDifference: number;
            if (effect.valueModifierType == 'SELF_CURRENT_HP_BASED') {
              HPDifference = Math.floor((averageValue * caster.storedHp) / 100);
            }
            if (effect.valueModifierType == 'TARGET_CURRENT_HP_BASED') {
              HPDifference = Math.floor((averageValue * storedHp) / 100);
            }
            if (effect.valueModifierType == 'STR_BASED') {
              HPDifference = Math.floor((averageValue * (caster.strength + caster.statusEffects.strengthBonus.value) / 100));
            }
            if (effect.valueModifierType == 'FLAT_VALUE') {
              HPDifference = Math.floor(averageValue);
            }
            if (effect.skillStatusEffect == 'DEAL_DAMAGE') {
              let colorDependentDamageAmplifier = this.colorMaps.find(set => set.colorName === caster.fighterModelReferenceDto.colorName).colorDamages[target.fighterModelReferenceDto.colorName];
              if (!Boolean(colorDependentDamageAmplifier)) {
                colorDependentDamageAmplifier = 100;
              }
              HPDifference = Math.floor(HPDifference * (colorDependentDamageAmplifier / 100)
                * (-100 / (100 + target.armor + target.statusEffects.armorBonus.value)));
            }
            console.log(HPDifference);
            target.currentHp = Math.min(Math.max(target.currentHp + HPDifference, 0), target.maximumHp);
            console.log(target.currentHp);
            console.log(target.maximumHp);
          } else if(effect.result!=0) {
            if (effect.skillStatusEffect !== 'STUN') {
              let value = Math.floor(averageValue);
              let parameterName = effect.skillStatusEffect.substring(effect.skillStatusEffect.indexOf("_") + 1).toLowerCase();
              let direction = effect.skillStatusEffect.substring(0, effect.skillStatusEffect.indexOf("_")).toLowerCase();
              let dto = {
                value: direction == 'increase' ? value : (value * -1),
                duration: 3
              };
              if (parameterName == "armor") {
                target.statusEffects.armorBonus.bonuses.push(dto);
                target.statusEffects.armorBonus.value = this.calculateFromBonuses(target.statusEffects.armorBonus.bonuses);
              } else if (parameterName == 'speed') {
                target.statusEffects.speedBonus.bonuses.push(dto);
                target.statusEffects.speedBonus.value = this.calculateFromBonuses(target.statusEffects.speedBonus.bonuses);
              } else if (parameterName == 'strength') {
                target.statusEffects.strengthBonus.bonuses.push(dto);
                target.statusEffects.strengthBonus.value = this.calculateFromBonuses(target.statusEffects.strengthBonus.bonuses);
              }
            } else {
              target.statusEffects.stunnedForTurns += Math.floor(averageValue);
            }
          }
          effectTargets.forEach(target=>{
            if(playerFighters.filter(fighter=>fighter.id===target.id).length>0){
             playerFighters[playerFighters.indexOf(playerFighters.find(fighter=>fighter.id===target.id))]=target;
            }
            if(agentFighters.filter(fighter=>fighter.id===target.id).length>0){
              agentFighters[agentFighters.indexOf(agentFighters.find(fighter=>fighter.id===target.id))]=target;
            }
          })
          });
      })
    })
    let scores=[];
    scores.push(
      this.calculateOverallBalance(agentFighters,playerFighters)
      * this.agent.overallBalancePriority);
    scores.push(this.calculateInternalBalance(playerFighters,false)
      * this.agent.enemyInternalBalancePriority);
    scores.push(this.calculateInternalBalance(agentFighters,true)
      * this.agent.allyInternalBalancePriority);
    scores.push(this.calculateIndividualScore(playerFighters,false)
      * this.agent.individualEnemyPriority);
    scores.push(this.calculateIndividualScore(agentFighters,true)
      * this.agent.individualAllyPriority);

    console.log(scores);
    // scores.push(this.calculateDamageOutputScore(playerFighters,agentFighters.find(fighter=>fighter.id===this.currentFighter.id))
    //   * this.agent.damageOutputPriority);
    this.agentFighters=JSON.parse(agentFightersCopy);
    this.playerFighters=JSON.parse(playerFightersCopy);
    return scores.reduce((a,b)=>a+b);
  }
  calculateFromBonuses(dtoList){
    if(dtoList.length>0) {
      return dtoList.map(dto => dto.value).reduce((a, b) => {
        return a + b;
      });
    } else{
      return 0;
    }
  }
}
