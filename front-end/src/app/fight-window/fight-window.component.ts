import {Component, OnDestroy, OnInit} from '@angular/core';
import {UserService} from "../services/user.service";
import {TokenStorageService} from "../auth/token-storage.service";
import {FightService} from "../services/fight.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-fight-window',
  templateUrl: './fight-window.component.html',
  styleUrls: ['./fight-window.component.css']
})
export class FightWindowComponent implements OnInit, OnDestroy {

  you:any;
  opponent:any;
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
              private fightService:FightService, private router:Router) { }

  ngOnInit() {
    this.turn = 0;
    this.fightService.findFightInProgressForUser(this.token.getUsername()).subscribe(res => {
      this.currentFight = res;
      if (this.currentFight.playerOne.login === this.token.getUsername()) {
        this.you = this.currentFight.playerOne;
        this.opponent = this.currentFight.playerTwo;
      } else {
        this.you = this.currentFight.playerTwo;
        this.opponent = this.currentFight.playerOne;
      }
      this.allFighters = this.you.allFighterList;
      this.allFighters = this.allFighters.concat(this.opponent.allFighterList);
      this.allFighters.forEach(fighter=>fighter.statusEffects={
        dead:false,
        armorBonus:[],
        speedBonus:[],
        strengthBonus:[],
        stunnedForTurns:0
      });
      this.fightInterval = setInterval(() => {
        this.fightService.getFightById(this.currentFight.id).subscribe(res => {
          this.currentFight = res;
          if (this.currentFight.fightStatus == 'ABANDONED') {
            sessionStorage.setItem("fightStatus", "");
            this.router.navigate(['home']);
          }
        })
      }, 3000);
      this.actionInterval = setInterval(() => {
        this.fightService.getActionListForFight(this.currentFight.id).subscribe(res => {
          this.applyEffects(res);
          if (this.turnOrder.length > 0) {
            this.currentFighter = this.allFighters.find
            (fighter => fighter.id === this.turnOrder[this.actionList.length % 8].fighterId);
          }
          if ((this.turnOrder.length == 0) ||
            (this.actionList.length != 0 && this.actionList[this.actionList.length - 1].nextActiveFighterId === 0 && this.turn === Math.floor(this.actionList.length / 8))) {
            let fighterSpeeds = this.allFighters.map(fighter => ({
              fighterId: fighter.id,
              speed: fighter.speed
            }));
            let fightCombatDto = {
              fightId: this.currentFight.id,
              fighterSpeedDtos: fighterSpeeds
            };
            this.turn = Math.floor(this.actionList.length / 8) + 1;
            this.fightService.getTurnOrderForFightAndTurn(fightCombatDto, this.turn).subscribe(res => {
              this.turnOrder = res;
            })
          }
        })
      }, 3000);
    })
  }
  selectSkill(skill){
    if(this.you.allFighterList.includes(this.currentFighter)){
      this.currentSkill=skill;
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
          console.log(res);
          this.currentSkill=undefined;
        });
      }
    }
  }
  private checkIfValid(skill,fighter):boolean{
    if(fighter.statusEffects.dead){
      return false;
    }
    let targetTypes=[].concat.apply([],skill.skillEffectBundles.map(bundle=>bundle.skillEffectDtos.map(dto=>dto.targetType)));
    if(targetTypes.some(type => ['TARGET_ENEMY', 'RANDOM_ENEMY', 'ALL_ENEMY_UNITS', 'ALL_UNITS'].includes(type))){
      return this.opponent.allFighterList.includes(fighter);
    } else{
      if (targetTypes.some(type => ['TARGET_ALLY', 'RANDOM_ALLY', 'ALL_ALLIED_UNITS', 'ALL_UNITS'].includes(type))
        && this.you.allFighterList.includes(fighter)) {
        return true;
      } else if(targetTypes.includes('THIS_UNIT' && fighter===this.currentFighter)){
        return true;
      }
      return false;
    }
  }
  abandonFight(){
    let fight=this.currentFight;
    fight.fightStatus='ABANDONED';
    this.service.challenge(fight).subscribe(res=>{
      console.log(res);
    })
  }

  ngOnDestroy(): void {
    clearInterval(this.actionInterval);
    clearInterval(this.fightInterval);
  }
  capitalize(string) {
    return string.charAt(0).toUpperCase() + string.slice(1);
  }
  applyEffects(actionList:any):void{

    if(this.actionList.length!==actionList.length){
      this.actionList=actionList;
      this.actionList
        .filter(action=>action.id>this.lastActionId)
        .forEach(action=>{
          console.log(action);
        let caster=this.allFighters.find(fighter=>fighter.id==action.activeFighterId);
        this.allFighters.forEach(fighter=>{
          fighter.storedHp=fighter.currentHp;
        });
        action.skillEffectResultDtoList.forEach(effectDto=> {
          let target =  this.allFighters.find(fighter=>fighter.id==effectDto.targetId)
          if(effectDto.statusEffect=='DEAL_DAMAGE' || effectDto.statusEffect=='RESTORE_HEALTH'){
            let HPDifference:number;
            if(effectDto.modifierType=='SELF_CURRENT_HP_BASED'){
              HPDifference=Math.floor((effectDto.result*caster.storedHp)/100);
            }
            if(effectDto.modifierType=='TARGET_CURRENT_HP_BASED'){
              HPDifference=Math.floor((effectDto.result*target.storedHp)/100);
            }
            if(effectDto.modifierType=='STR_BASED'){
              HPDifference=Math.floor((effectDto.result*caster.strength)/100);
            }
            if(effectDto.modifierType=='FLAT_VALUE'){
              HPDifference=Math.floor(effectDto.result);
            }
            if(effectDto.statusEffect=='DEAL_DAMAGE'){
              HPDifference*=-1;
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
              console.log(effectDto);

              let value=Math.floor(effectDto.result);
              let parameterName=effectDto.statusEffect.substring(effectDto.statusEffect.indexOf("_")+1).toLowerCase();
              let direction = effectDto.statusEffect.substring(0,effectDto.statusEffect.indexOf("_")).toLowerCase();
              let tooltip=this.capitalize(parameterName)+" "+direction+" by "+value;
              let dto={
                value:direction=='increase'?value:(value*-1),
                tooltip:tooltip,
                duration:3
              };
              if(parameterName=="armor"){
                target.statusEffects.armorBonus.push(dto);
              } else if(parameterName=='speed'){
                target.statusEffects.speedBonus.push(dto);
              } else if(parameterName=='strength'){
                target.statusEffects.strengthBonus.push(dto);
              }
              this.fightLog.push((this.you.allFighterList.includes(target) ? "Your " : "Enemy's ") + target.fighterModelReferenceDto.colorName + " " + target.fighterModelReferenceDto.shapeName +
                "'s "+parameterName+" has been "+direction +"d by "+value+"!");
            } else{
              target.statusEffects.stunnedForTurns+=effectDto.result;
              this.fightLog.push((this.you.allFighterList.includes(target) ? "Your " : "Enemy's ") + target.fighterModelReferenceDto.colorName + " " + target.fighterModelReferenceDto.shapeName +
                " has been stunned for "+effectDto.result+" turns!");
            }
          }
        }
        );
        this.lastActionId=action.id;
      });
      console.log(this.actionList);
      console.log(this.allFighters);
    }
  }

}
