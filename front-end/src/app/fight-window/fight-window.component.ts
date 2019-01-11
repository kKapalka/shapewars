import { Component, OnInit } from '@angular/core';
import {UserService} from "../services/user.service";
import {TokenStorageService} from "../auth/token-storage.service";
import {FightService} from "../services/fight.service";

@Component({
  selector: 'app-fight-window',
  templateUrl: './fight-window.component.html',
  styleUrls: ['./fight-window.component.css']
})
export class FightWindowComponent implements OnInit {

  you:any;
  opponent:any;
  allFighters:any[]=[];
  currentFight:any;
  actionList:any[]=[];
  turnOrder:any=[];
  turn:any;
  interval:any;
  currentSkill:any;
  fightLog:string[]=[];
  currentFighter:any={
    shapeSkillSet:[
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
  };
  constructor(private service:UserService, private token:TokenStorageService,
              private fightService:FightService) { }

  ngOnInit() {
    this.turn=0;
    this.service.getPlayerData(this.token.getUsername()).subscribe(res=>{
      this.you=res;
      this.fightService.getCombatantsByUser(this.token.getUsername()).subscribe(res=>{
        this.you.allFighterList=res;
      });
    });
    this.service.getFightsByUser(this.token.getUsername()).subscribe(res=>{
      this.currentFight=res.find(fight=>fight.fightStatus==='IN_PROGRESS');
      let opponentName:string="";
      if(this.currentFight.playerOne===this.token.getUsername()){
        opponentName=this.currentFight.playerTwo;
      } else{
        opponentName=this.currentFight.playerOne;
      }
      this.service.getPlayerData(opponentName).subscribe(res=>{
        this.opponent=res;
        this.fightService.getCombatantsByUser(opponentName).subscribe(res=>{
          this.opponent.allFighterList=res;
          this.allFighters=this.you.allFighterList;
          this.allFighters=this.allFighters.concat(this.opponent.allFighterList);
          this.interval=setInterval(()=>{
            this.fightService.getActionListForFight(this.currentFight.id).subscribe(res=>{
              this.actionList=res;
              if(this.turnOrder.length>0){
                this.currentFighter=this.allFighters.find
                (fighter=>fighter.id===this.turnOrder[this.actionList.length%8].fighterId);
              }
              if((this.turnOrder.length==0) ||
                (this.actionList.length!=0 && this.actionList[this.actionList.length-1].nextActiveFighterId===0 && this.turn===Math.floor(this.actionList.length/8))){
                let fighterSpeeds=this.allFighters.map(fighter=>({
                  fighterId:fighter.id,
                    speed:fighter.speed
                }));
                let fightCombatDto={
                  fightId:this.currentFight.id,
                  fighterSpeedDtos:fighterSpeeds
                };
                this.turn=Math.floor(this.actionList.length/8)+1;
                this.fightService.getTurnOrderForFightAndTurn(fightCombatDto,this.turn).subscribe(res=>{
                  this.turnOrder=res;
                })
              }
            })
          },3000);
        });
      });
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
        console.log(action);
        this.fightService.saveAction(action).subscribe(res=>{
          console.log(res);
        });
      }
    }
  }
  private checkIfValid(skill,fighter):boolean{
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
}
