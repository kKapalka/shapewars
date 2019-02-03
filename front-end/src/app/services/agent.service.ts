import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AgentService {

  agentFighters:any;
  playerFighters:any;
  selectedTarget:any;
  agent:any;
  colorMaps:any
  constructor() { }

  init(agentFighters:any,playerFighters:any,agent:any,colorMaps:any){
    this.agentFighters=agentFighters;
    this.playerFighters=playerFighters;
    this.agent=agent;
    this.colorMaps=colorMaps;
  }
  selectSkill(currentFighter:any){
    let agentScores=[];
    agentScores.push(
      this.calculateOverallBalance(this.playerFighters,this.agentFighters)
      * this.agent.overallBalancePriority);
    agentScores.push(this.calculateInternalBalance(this.playerFighters)
      * this.agent.enemyInternalBalancePriority);
    agentScores.push(this.calculateInternalBalance(this.agentFighters)
      * this.agent.allyInternalBalancePriority);
    agentScores.push(this.calculateIndividualScore(this.playerFighters)
      * this.agent.individualEnemyPriority);
    agentScores.push(this.calculateIndividualScore(this.agentFighters)
      * this.agent.individualAllyPriority);
    agentScores.push(this.calculateDamageOutputScore(this.playerFighters,currentFighter)
      * this.agent.damageOutputPriority);
    console.log(agentScores);
    let skillsInPriority = this.calculateSkillPriorities(currentFighter,agentScores);

  }
  getSelectedTarget(){
    return this.selectedTarget;
  }



  calculateOverallBalance(agentFighters:any,playerFighters:any):number{
    return agentFighters.filter(fighter=>!fighter.statusEffects.dead)
        .map(fighter=>(fighter.currentHp/fighter.maximumHp)*20+(fighter.currentMana/fighter.maximumMana)*5)
        .reduce((a,b)=>a+b)
     - playerFighters.filter(fighter=>!fighter.statusEffects.dead)
        .map(fighter=>(fighter.currentHp/fighter.maximumHp)*20+(fighter.currentMana/fighter.maximumMana)*5)
        .reduce((a,b)=>a+b);
  }
  calculateInternalBalance(fighters:any):number{
    return fighters.filter(fighter=>!fighter.statusEffects.dead)
      .map(fighter=>(100+(fighter.statusEffects.strengthBonus*3)+(fighter.statusEffects.armorBonus)+(fighter.statusEffects.speedBonus*2)-(fighter.statusEffects.stunnedForTurns*20))
        *((fighter.currentHp/fighter.maximumHp)*20+(fighter.currentMana/fighter.maximumMana)*5))
      .reduce((a,b)=>a+b)
  }
  calculateIndividualScore(fighters:any):number{
    return Math.max(fighters.filter(fighter=>!fighter.statusEffects.dead)
      .map(fighter=>(100+(fighter.statusEffects.strengthBonus*3)+(fighter.statusEffects.armorBonus)+(fighter.statusEffects.speedBonus*2)-(fighter.statusEffects.stunnedForTurns*20))
        *((fighter.currentHp/fighter.maximumHp)*20+(fighter.currentMana/fighter.maximumMana)*5))
      .reduce((a,b)=>a+b))*4
  }
  calculateDamageOutputScore(playerFighters:any,currentFighter:any):number{
    return 0;
  }

  calculateSkillPriorities(currentFighter:any,scores:any):any[]{
    return [];
  }

}
