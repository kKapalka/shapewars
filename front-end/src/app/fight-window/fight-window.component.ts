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
  allFighters:any[]=[];
  currentFight:any;
  actionList:any[]=[];
  turnOrder:any=[];
  turn:any;
  actionInterval:any;
  fightInterval:any;
  currentSkill:any;
  fightLog:string[]=[];
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
    console.log(this.token.getUsername());
    this.fightService.findFightInProgressForUser(this.token.getUsername()).subscribe(res => {

      this.currentFight = res;
      if (this.currentFight.playerOne === this.token.getUsername()) {
        this.you = this.currentFight.playerOne;
        this.opponent = this.currentFight.playerTwo;
      } else {
        this.you = this.currentFight.playerTwo;
        this.opponent = this.currentFight.playerOne;
      }
      this.allFighters = this.you.allFighterList;
      this.allFighters = this.allFighters.concat(this.opponent.allFighterList);
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
            console.log(fighterSpeeds)
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
          this.currentFight=undefined;
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

  ngOnDestroy(): void {
    clearInterval(this.actionInterval);
    clearInterval(this.fightInterval);
  }

  applyEffects(actionList:any):void{
    if(this.actionList.length!==actionList.length){
      this.actionList=actionList;
      console.log(this.actionList);
    }
  }

}
