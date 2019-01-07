import {Component, OnDestroy, OnInit} from '@angular/core';
import { UserService } from '../services/user.service';
import {TokenStorageService} from "../auth/token-storage.service";
import {ActivatedRoute, Router} from "@angular/router";
import {MessagesService} from "../services/messages.service";
import Message from "../dtos/message";
import {NgbModal, ModalDismissReasons} from '@ng-bootstrap/ng-bootstrap';
import {SkillsService} from '../services/skills.service';
import {Skill} from '../dtos/skill';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit, OnDestroy {
  user: object;
  profileUsername:string;
  closeResult:string;
  messageToSend:string;
  currentUser:boolean=false;
  messages:any=[];
  interval:any;
  selectedFighter:any;
  fighters:any=[];
  partyFighters:any=[];
  inventoryFighters:any=[];
  xpThreshold:any;
  skills:any;
  selectedFighterSkills:any;
  constructor(private token: TokenStorageService,private router:Router,
              private activatedRoute: ActivatedRoute,private service: UserService,
              private messageService:MessagesService, private modalService:NgbModal,
              private skillService: SkillsService) {
    this.retrieveUserData();
  }

  ngOnInit() {
    let lastMessageType = sessionStorage.getItem("LastLogType");
    if(lastMessageType && lastMessageType!=="WORKING"){
      window.location.href = "/error";
    }
    if(!this.checkIfThisPlayerProfile()) {
      this.interval = setInterval(() => {
        this.messageService.getMessagesByCallers([this.token.getUsername(), this.profileUsername])
          .subscribe(res => {
            if(this.messages!==res){
              this.messages=res;
            }
          })
      }, 1500);
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
  sendMessage(){
    let message:Message={
      sender:this.token.getUsername(),
      receiver:this.profileUsername,
      message:this.messageToSend
    };
    this.messageService.sendMessage(message).subscribe((res)=> {
      console.log(res);
      this.messageToSend="";
    },console.log);
  }
  ngOnDestroy(): void {
    clearInterval(this.interval);
  }
  challenge():any{
    let challenge={
      playerOne:this.token.getUsername(),
      playerTwo:this.profileUsername,
      fightStatus:"INVITE_PENDING"
    };
    this.messageService.challenge(challenge).subscribe(res=>{
      console.log(res);
    },console.log)
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
    this.selectedFighterSkills=this.skills.filter(skill=>this.selectedFighter.shapeSkillIDSet.includes(skill.id));
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
  
}
