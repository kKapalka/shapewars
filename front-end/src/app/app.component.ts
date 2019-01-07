import { Component, OnInit } from '@angular/core';
import { TokenStorageService } from './auth/token-storage.service';
import {MaintenanceService} from "./services/maintenance.service";
import {ModalDismissReasons, NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {MessagesService} from "./services/messages.service";
@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  private roles: string[];
  private authority: string;
  private lastLogMessage: any;
  private lastLogMessageType: string;
  private closeResult:string;
  constructor(private tokenStorage: TokenStorageService,
              private maintenanceService:MaintenanceService,
              private modalService:NgbModal){
  }

  ngOnInit() {
    this.maintenanceService.getLastMaintenanceLogMessage().subscribe(res=>{
      this.lastLogMessage = res;
      this.lastLogMessageType = this.lastLogMessage.messageType;
      localStorage.setItem("LastLogType",this.lastLogMessageType);
    });
    if (this.tokenStorage.getToken()) {
      this.roles = this.tokenStorage.getAuthorities();
      console.log(this.roles);
      this.roles.forEach(role => {
        if (role === 'ROLE_ADMIN') {
          this.authority = 'admin';
        } else if (role === 'ROLE_PM') {
          this.authority = 'pm';
        } else if(role === 'ROLE_USER'){
          this.authority = 'user';
        }
      });
    }

  }

  onLogout(event){
    event.preventDefault();
    this.tokenStorage.signOut();
    window.location.pathname="/home";
  }
  openMaintenanceMessage(content) {
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
