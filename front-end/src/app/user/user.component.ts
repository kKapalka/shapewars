import {Component, OnDestroy, OnInit} from '@angular/core';
import { UserService } from '../services/user.service';
import {TokenStorageService} from "../auth/token-storage.service";
import {ActivatedRoute, Router} from "@angular/router";
import {MessagesService} from "../services/messages.service";
import Message from "../dtos/message";
import {NgbModal, ModalDismissReasons} from '@ng-bootstrap/ng-bootstrap';
import {SkillsService} from '../services/skills.service';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit, OnDestroy {
  user: object={};
  profileUsername:string="";
  closeResult:string="";
  messageToSend:string="";
  currentUser:boolean=false;
  messages:any=[];
  interval:any={};
  selectedFighter:any;
  fighters:any=[];
  partyFighters:any=[];
  inventoryFighters:any=[];
  challengeIssued:any={};
  skills:any=[];
  selectedFighterSkills:any=[];
  fights={
    won:0,
    lost:0,
    abandoned:0
  };
  constructor(private token: TokenStorageService,private router:Router,
              private activatedRoute: ActivatedRoute,private service: UserService,
              private messageService:MessagesService, private modalService:NgbModal,
              private skillService: SkillsService) {
    this.retrieveUserData();
  }

  ngOnInit() {
    this.service.getFightsByUser(this.profileUsername).subscribe(res=>{
      this.fights={
        won:res.filter(fight=>fight.winnerName===this.profileUsername).length,
        lost:res.filter(fight=>fight.fightStatus==='FINISHED' && fight.winnerName!==this.profileUsername).length,
        abandoned:(fight=>fight.fightStatus==='ABANDONED').length
      };
    })
    if(!this.checkIfThisPlayerProfile()) {
      this.interval = setInterval(() => {
        this.messageService.getMessagesByCallers([this.token.getUsername(), this.profileUsername])
          .subscribe(res => {
            if(this.messages!==res){
              this.messages=res;
            }
          })
      }, 1500);
      this.service.getChallengesByChallenger(this.profileUsername).subscribe(res=>{
        this.challengeIssued=res;
      })
    } else{
      this.service.getFightersByUser(this.token.getUsername()).subscribe(res=>{
        this.fighters=res;
        this.inventoryFighters=res.filter(fighter=>fighter.slot==="INVENTORY");
        this.partyFighters=res.filter(fighter=>fighter.slot!=='INVENTORY').sort((a,b)=>{return a.slot.localeCompare(b.slot)});
      });
      this.skillService.getAllSkills().subscribe(res=>{
        this.skills=res;
      })
    }
  }
  checkIfThisPlayerProfile():boolean{
    return this.profileUsername===null || this.profileUsername===this.token.getUsername()
}
  retrieveUserData(){
    this.profileUsername=this.activatedRoute.snapshot.paramMap.get('username');
    if(this.checkIfThisPlayerProfile()){
      this.currentUser=true;
      this.profileUsername=this.token.getUsername();
    }
    this.service.getPlayerData(this.profileUsername).subscribe(res=>{
      this.user=res;
    },console.log);
  }
  sendMessage(event){
    event.preventDefault();
    if(this.messageToSend.length>0) {
      let message: Message = {
        sender: this.token.getUsername(),
        receiver: this.profileUsername,
        message: this.messageToSend
      };
      this.messageService.sendMessage(message).subscribe((res) => {
        this.messageToSend = "";
      }, console.log);
    }
  }
  ngOnDestroy(): void {
    clearInterval(this.interval);
  }
  challenge():any{
    this.service.getPlayerData(this.token.getUsername()).subscribe(res=>{
      if(res.allFighterList.filter(fighter=>fighter.slot!=='INVENTORY').length!==4){
        console.log("You must have complete party before challenging another player!");
      } else{
        let challenge={
          playerNames:[this.token.getUsername(),this.profileUsername],
          fightStatus:"INVITE_PENDING"
        };
        this.service.challenge(challenge).subscribe(res=>{
          console.log(res);
        },console.log)
      }
    });

  }

  set(fighter,slot){
    if(slot!=='INVENTORY'){
      let currentSlotFighter=this.fighters.find(fighter=>fighter.slot===slot);
      if(Boolean(currentSlotFighter)){
        currentSlotFighter.slot='INVENTORY';
      }
    }
    fighter.slot=slot;
    this.inventoryFighters=this.fighters.filter(fighter=>fighter.slot==="INVENTORY");
    this.partyFighters=this.fighters.filter(fighter=>fighter.slot!=='INVENTORY').sort((a,b)=>{return a.slot.localeCompare(b.slot)});
    this.service.saveFighter(fighter).subscribe(res=>{
      console.log(res);
    });
  }

  openDetailsForFighter(fighter,content) {
    this.selectedFighter=fighter;
    this.selectedFighterSkills=this.skills.filter(skill=>this.selectedFighter.fighterModelReferrenceDto.skillSet.includes(skill.id));
    this.modalService.open(content, {ariaLabelledBy: 'modal-basic-title'}).result.then((result) => {
      this.closeResult = `Closed with: ${result}`;
    }, (reason) => {
      this.closeResult = `Dismissed ${this.getDismissReason(reason)}`;
    });
  }
  private getDismissReason(reason: any): string {
    if (reason === ModalDismissReasons.ESC) {
      return 'by pressing ESC';
    } else if (reason === ModalDismissReasons.BACKDROP_CLICK) {
      return 'by clicking on a backdrop';
    } else {
      return  `with: ${reason}`;
    }
  }
  acceptChallenge(){
    this.service.getPlayerData(this.token.getUsername()).subscribe(res=> {
      if (res.allFighterList.filter(fighter => fighter.slot !== 'INVENTORY').length !== 4) {
        console.log("You must have complete party before accepting a challenge from another player!");
      } else {
        this.challengeIssued.fightStatus = "IN_PROGRESS";
        this.service.challenge(this.challengeIssued).subscribe(res => {
          console.log(res);
          this.challengeIssued = null;
        });
      }
    });
  }
  rejectChallenge(){
    this.challengeIssued.fightStatus="INVITE_REJECTED";
    this.service.challenge(this.challengeIssued).subscribe(res=>{
      console.log(res);
      this.challengeIssued=null;
    })
  }
}
