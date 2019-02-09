import {Component, OnDestroy, OnInit} from '@angular/core';
import {UserService} from "../services/user.service";
import {TokenStorageService} from "../auth/token-storage.service";
import {FightService} from "../services/fight.service";
import {Router} from "@angular/router";
import {ShapeService} from "../services/shape.service";
import {ColormapService} from "../services/colormap.service";
import {AgentService} from "../services/agent.service";

@Component({
  selector: 'app-fight-window',
  templateUrl: './fight-window.component.html',
  styleUrls: ['./fight-window.component.css']
})
export class FightWindowComponent implements OnInit, OnDestroy {

  you:any;
  opponent:any;
  agent:any;
  allFighters:any=[];
  currentFight:any;
  actionList:any=[];
  turnOrder:any=[];
  turn:any;
  actionInterval:any;
  fightInterval:any;
  currentSkill:any;
  fightLog:string[]=[];
  lastActionId:number=0;
  winner:any;
  currentFightStatus:string="";
  colorSet:any=[]
  currentFighter:any={
    fighterModelReferenceDto: {
      skillSet:[
        {
          icon:""
        },

        {
          icon:""
        },
        {
          icon:""
        },
        {
          icon:""
        }
      ]
    }
  };
  constructor(private service:UserService, private token:TokenStorageService,
              private fightService:FightService, private router:Router,
              private colorMapService:ColormapService, private agentService:AgentService) {
    this.colorMapService.getAllColors().subscribe(res=>{
      this.colorSet=res.map(color=>{
        let colorDamages={};
        color.colorDamageDtoList.forEach(dto=>{
          colorDamages[dto.enemyColorName]=dto.damagePercentage;
        })
        let colorDamageSet={
          colorName:color.colorName,
          colorDamages:colorDamages
        }
        return colorDamageSet;
      });
    });
  }

  ngOnInit() {
    this.turn = 0;
    this.fightService.findFightInProgressForUser(this.token.getUsername()).subscribe(res => {
      this.currentFight = res;
      this.you=this.currentFight.players.find(player=>player.login===this.token.getUsername());
      this.you.allFighterList=this.you.allFighterList.sort((a,b)=>{
        if (a.slot < b.slot)
          return -1;
        if (a.slot > b.slot)
          return 1;
        return 0;
      });
      console.log(this.you.allFighterList);
      this.opponent=this.currentFight.players.filter(player=>player!=this.you)[0];
      this.opponent.allFighterList=this.opponent.allFighterList.sort((a,b)=>{
        if (a.slot < b.slot)
          return -1;
        if (a.slot > b.slot)
          return 1;
        return 0;
      });
      if(this.opponent.login.substring(0,9)==='BOT_LEVEL'){
        this.service.getAgentForUsername(this.token.getUsername()).subscribe(res=>{
          this.agent=res;
          this.agentService.init(this.opponent.allFighterList,this.you.allFighterList,this.agent,this.colorSet);
        })
      }
      this.allFighters = this.you.allFighterList;
      this.allFighters = this.allFighters.concat(this.opponent.allFighterList);
      this.allFighters.forEach(fighter => fighter.statusEffects = {
        dead: false,
        armorBonus: {
          value: 0,
          bonuses: []
        },
        speedBonus: {
          value: 0,
          bonuses: []
        },
        strengthBonus: {
          value: 0,
          bonuses: []
        },
        stunnedForTurns: 0
      });
      this.fightInterval = setInterval(() => {
        this.fightService.getFightById(this.currentFight.id).subscribe(res => {
          this.currentFightStatus = res.fightStatus;
          this.attemptFinalizeFight();
          if (this.currentFightStatus === 'FINISHED') {
            clearInterval(this.fightInterval);
            clearInterval(this.actionInterval);
            if (this.winner === this.you) {
              alert('You have won the fight!')
            } else {
              alert('You have lost the fight!')
            }
            if(this.opponent.login.substring(0,9)==='BOT_LEVEL'){
              this.service.onBattleFinish({
                id:this.currentFight.id,
                playerNames:this.currentFight.players.map(player=>player.login),
                fightStatus:this.currentFightStatus,
                relevantUsername:this.winner.login,
              }).subscribe(res=>{
                console.log(res)
              });
            }
            sessionStorage.setItem("fightStatus", "");
          }
          if (this.currentFightStatus == 'ABANDONED') {
            sessionStorage.setItem("fightStatus", "");
            this.router.navigate(['home']);
          }
        })
      }, 1500);
      this.actionInterval = this.initActionInterval();
    });
  }
  initActionInterval(){
       return  setInterval(() => {
          this.fightService.getActionListForFight(this.currentFight.id).subscribe(res => {
            this.applyEffects(res);
            if (this.turnOrder.length > 0) {
              this.currentFighter = this.allFighters.find
              (fighter => fighter.id === this.turnOrder[this.actionList.length % 8].fighterId);
              if((this.currentFighter.statusEffects.stunnedForTurns>0 || this.currentFighter.statusEffects.dead) && this.you.allFighterList.includes(this.currentFighter)){
                this.currentSkill={
                  id:0
                };
                this.performAttack({
                  id:0
                });
              } else if(this.opponent.login.substring(0,9)==='BOT_LEVEL' && this.opponent.allFighterList.includes(this.currentFighter)){
                clearInterval(this.actionInterval);
                if((this.currentFighter.statusEffects.stunnedForTurns>0 || this.currentFighter.statusEffects.dead)){
                  this.currentSkill={
                    id:0
                  };
                  this.performAttack({
                    id:0
                  });
                } else{
                  this.executeBotAttack();
                }
              }
            }
            if (this.turnOrder.filter(turn=>turn.turn===(Math.floor(this.actionList.length/8)+1)).length==0) {
              let fighterSpeeds = this.allFighters.map(fighter => ({
                fighterId: fighter.id,
                speed: fighter.speed + fighter.statusEffects.speedBonus.value
              }));
              let fightCombatDto = {
                fightId: this.currentFight.id,
                fighterSpeedDtos: fighterSpeeds
              };
              this.turn = Math.floor(this.actionList.length / 8) + 1;
              this.fightService.getTurnOrderForFightAndTurn(fightCombatDto, this.turn).subscribe(res => {
                this.turnOrder = res;
              })
              if(this.turn>1 && this.opponent.login.substring(0,9)==='BOT_LEVEL'){
                console.log("learning set update triggered!");
                this.service.updateAgentLearningSet(this.currentFight,this.turn).subscribe(res=>{
                  console.log(res)
                });
              }
            }
          })
        }, 1500);
      }
  selectSkill(skill){
    if(this.you.allFighterList.includes(this.currentFighter) && !this.currentFighter.statusEffects.dead && this.currentFighter.statusEffects.stunnedForTurns===0){
      if(this.currentFighter.currentMana>=skill.cost){
        this.currentSkill=skill;
      }
    }
  }
  performAttack(fighter){
    if(this.currentSkill!==undefined){
      if(this.checkIfValid(this.currentSkill,fighter)){
        let action={
          fightId:this.currentFight.id,
          activeFighterId:this.currentFighter.id,
          selectedTargetId:fighter.id,
          nextActiveFighterId:this.currentFighter.id==this.turnOrder[7].fighterId?0:
            this.turnOrder[this.actionList.length%8+1].fighterId,
          skillId:this.currentSkill.id
        };
        this.fightService.saveAction(action).subscribe(res=>{
          this.currentSkill=undefined;
          if(this.opponent.login.substring(0,9)==='BOT_LEVEL' && this.opponent.allFighterList.includes(this.currentFighter)){
            this.actionInterval = this.initActionInterval();
          }
        });
      }
    }
  }
  private checkIfValid(skill,fighter):boolean{
    if(skill.id==0 && fighter.id==0){
      return true;
    }
    if(fighter.statusEffects.dead){
      return false;
    }
    let targetTypes=[].concat.apply([],skill.skillEffectBundles.map(bundle=>bundle.skillEffectDtos.map(dto=>dto.targetType)));
    let enemyTargets;
    let alliedTargets;
    if(this.opponent.allFighterList.includes(this.currentFighter)){
      enemyTargets=this.you.allFighterList;
      alliedTargets=this.opponent.allFighterList;
    } else{
      alliedTargets=this.you.allFighterList;
      enemyTargets=this.opponent.allFighterList;
    }
    if(targetTypes.some(type => ['TARGET_ENEMY', 'RANDOM_ENEMY', 'ALL_ENEMY_UNITS', 'ALL_UNITS'].includes(type))){
      return enemyTargets.includes(fighter);
    } else{
      if (targetTypes.some(type => ['TARGET_ALLY', 'RANDOM_ALLY', 'ALL_ALLIED_UNITS', 'ALL_UNITS'].includes(type))
        && alliedTargets.includes(fighter)) {
        return true;
      } else if(targetTypes.includes('THIS_UNIT' && fighter===this.currentFighter) && !(targetTypes.includes('TARGET_ENEMY') && targetTypes.includes('TARGET_ALLY'))){
        return true;
      }
      return false;
    }
  }
  abandonFight(){
    let fightBase={
      id:this.currentFight.id,
      playerNames:this.currentFight.players.map(player=>player.login),
      fightStatus:'ABANDONED',
      relevantUsername:this.token.getUsername()
    };
    this.service.challenge(fightBase).subscribe(res=>{
      console.log(res);
    })
  }
  attemptFinalizeFight(){
    if(this.you.allFighterList.map(fighter=>fighter.currentHp).reduce((a,b)=>a+b)==0){
      this.winner=this.opponent;
    }
    if(this.opponent.allFighterList.map(fighter=>fighter.currentHp).reduce((a,b)=>a+b)==0){
      this.winner=this.you;
    }
    if(Boolean(this.winner) && this.currentFightStatus=='IN_PROGRESS'){
      let fightBase={
        id:this.currentFight.id,
        playerNames:this.currentFight.players.map(player=>player.login),
        fightStatus:'FINISHED',
        relevantUsername:this.winner.login
      };
      this.service.challenge(fightBase).subscribe(res=>{
        console.log(res);
      })
    }
  }
  ngOnDestroy(): void {
    clearInterval(this.actionInterval);
    clearInterval(this.fightInterval);
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
  abs(value){
    return Math.abs(value);
  }
  applyEffects(actionList:any):void{

    if(this.actionList.length!==actionList.length){
      this.actionList=actionList;
      this.actionList
        .filter(action=>action.id>this.lastActionId)
        .forEach(action=>{
        let caster=this.allFighters.find(fighter=>fighter.id==action.activeFighterId);
        if(Boolean(action.skillId)){
          caster.currentMana=Math.max(Math.min(caster.currentMana - caster.skillSet.find(skill=>skill.id==action.skillId).cost,caster.maximumMana),0);
        }
        caster.statusEffects.armorBonus.bonuses.forEach(bonus=>{
            bonus.duration--;
            if(bonus.duration==0){
              caster.statusEffects.armorBonus.bonuses.splice(caster.statusEffects.armorBonus.bonuses.indexOf(bonus,1));
            }
          });
        caster.statusEffects.armorBonus.value=this.calculateFromBonuses(caster.statusEffects.armorBonus.bonuses);
          caster.statusEffects.strengthBonus.bonuses.forEach(bonus=>{
            bonus.duration--;
            if(bonus.duration==0){
              caster.statusEffects.strengthBonus.bonuses.splice(caster.statusEffects.strengthBonus.bonuses.indexOf(bonus,1));
            }
          });
          caster.statusEffects.strengthBonus.value=this.calculateFromBonuses(caster.statusEffects.strengthBonus.bonuses);
          caster.statusEffects.speedBonus.bonuses.forEach(bonus=>{
            bonus.duration--;
            if(bonus.duration==0){
              caster.statusEffects.speedBonus.bonuses.splice(caster.statusEffects.speedBonus.bonuses.indexOf(bonus,1));
            }
          });
          caster.statusEffects.speedBonus.value=this.calculateFromBonuses(caster.statusEffects.speedBonus.bonuses);
          if(caster.statusEffects.stunnedForTurns>0){
            caster.statusEffects.stunnedForTurns--;
          }
        this.allFighters.forEach(fighter=>{
          fighter.storedHp=fighter.currentHp;
        });
        action.skillEffectResultDtoList.forEach(effectDto=> {
          let target =  this.allFighters.find(fighter=>fighter.id==effectDto.targetId)
          if(!target.statusEffects.dead){
            if((effectDto.statusEffect=='DEAL_DAMAGE' || effectDto.statusEffect=='RESTORE_HEALTH')){
              let HPDifference:number;
              if(effectDto.modifierType=='SELF_CURRENT_HP_BASED'){
                HPDifference=Math.floor((effectDto.result*caster.storedHp)/100);
              }
              if(effectDto.modifierType=='TARGET_CURRENT_HP_BASED'){
                HPDifference=Math.floor((effectDto.result*target.storedHp)/100);
              }
              if(effectDto.modifierType=='STR_BASED'){
                HPDifference=Math.floor((effectDto.result*(caster.strength+caster.statusEffects.strengthBonus.value)/100));
              }
              if(effectDto.modifierType=='FLAT_VALUE'){
                HPDifference=Math.floor(effectDto.result);
              }
              if(effectDto.statusEffect=='DEAL_DAMAGE'){
                let colorDependentDamageAmplifier=this.colorSet.find(set=>set.colorName===caster.fighterModelReferenceDto.colorName).colorDamages[target.fighterModelReferenceDto.colorName];
                if(!Boolean(colorDependentDamageAmplifier)){
                  colorDependentDamageAmplifier=100;
                }
                HPDifference=Math.floor(HPDifference*(colorDependentDamageAmplifier/100)
                  *(-100/(100+target.armor+target.statusEffects.armorBonus.value)));
              }
              target.currentHp=Math.min(Math.max(target.currentHp+HPDifference, 0), target.maximumHp);
              if(HPDifference!=0) {
                this.fightLog.push(
                  (this.you.allFighterList.includes(target) ? "Your " : "Enemy's ") + target.fighterModelReferenceDto.colorName + " " + target.fighterModelReferenceDto.shapeName +
                  (effectDto.statusEffect == 'DEAL_DAMAGE' ? " has taken " + (HPDifference * -1) + " damage!" : " has restored " + HPDifference + " health!"));
              }
              if(target.currentHp==0){
                target.statusEffects.dead=true;
                this.fightLog.push((this.you.allFighterList.includes(target) ? "Your " : "Enemy's ") + target.fighterModelReferenceDto.colorName + " " + target.fighterModelReferenceDto.shapeName +
                  " has died!");
              }
            } else if(effectDto.result!=0){
              if (effectDto.statusEffect!=='STUN'){

                let value=Math.floor(effectDto.result);
                let parameterName=effectDto.statusEffect.substring(effectDto.statusEffect.indexOf("_")+1).toLowerCase();
                let direction = effectDto.statusEffect.substring(0,effectDto.statusEffect.indexOf("_")).toLowerCase();
                let dto={
                  value:direction=='increase'?value:(value*-1),
                  duration:3
                };
                if(parameterName=="armor"){
                  target.statusEffects.armorBonus.bonuses.push(dto);
                  target.statusEffects.armorBonus.value=this.calculateFromBonuses(target.statusEffects.armorBonus.bonuses);
                } else if(parameterName=='speed'){
                  target.statusEffects.speedBonus.bonuses.push(dto);
                  target.statusEffects.speedBonus.value=this.calculateFromBonuses(target.statusEffects.speedBonus.bonuses);
                } else if(parameterName=='strength'){
                  target.statusEffects.strengthBonus.bonuses.push(dto);
                  target.statusEffects.strengthBonus.value=this.calculateFromBonuses(target.statusEffects.strengthBonus.bonuses);
                }
                this.fightLog.push((this.you.allFighterList.includes(target) ? "Your " : "Enemy's ") + target.fighterModelReferenceDto.colorName + " " + target.fighterModelReferenceDto.shapeName +
                  "'s "+parameterName+" has been "+direction +"d by "+value+"!");
              } else{
                target.statusEffects.stunnedForTurns+=Math.floor(effectDto.result);
                this.fightLog.push((this.you.allFighterList.includes(target) ? "Your " : "Enemy's ") + target.fighterModelReferenceDto.colorName + " " + target.fighterModelReferenceDto.shapeName +
                  " has been stunned for "+effectDto.result+" turns!");
              }
            }
          }
        }
        );
        this.lastActionId=action.id;
      });
    }
  }
  styleImages(skill){
    let styles:any={};
    if(skill===this.currentSkill){
      styles.border='2px solid green';
    }
    if(skill.cost>this.currentFighter.currentMana){
      styles['webkit-filter']='grayscale(100%)';
      styles.filter='grayscale(100%)';
    }
    return styles;
  }
  executeBotAttack(){
    setTimeout(()=>{
      let selectedSkill =this.agentService.selectSkill(this.opponent.allFighterList,this.you.allFighterList,this.currentFighter);
      this.currentSkill = this.currentFighter.skillSet.find(skill=>skill===selectedSkill);
      setTimeout(()=>{
        this.performAttack(this.allFighters.find(fighter=>fighter.id===this.agentService.getSelectedTarget()));
      },1500);
    },1500);
  }
}
