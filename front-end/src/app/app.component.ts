import {Component, OnDestroy, OnInit} from '@angular/core';
import { TokenStorageService } from './auth/token-storage.service';
import {MaintenanceService} from "./services/maintenance.service";
import {UserService} from "./services/user.service";
import {Router} from "@angular/router";
@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit, OnDestroy {
  private roles: string[];
  private authority: string;
  private lastLogMessageType: string;
  private interval:any;
  constructor(private tokenStorage: TokenStorageService,
              private maintenanceService:MaintenanceService,
              private userService:UserService,
              private router:Router){
  }

  ngOnInit() {
    this.maintenanceService.getLastMaintenanceLogMessage().subscribe(res=>{
      this.lastLogMessageType = res.messageType;
      sessionStorage.setItem("maintenanceMessageType",this.lastLogMessageType);
    });
    if (this.tokenStorage.getToken()) {
      this.interval=setInterval(()=>{
        if(this.tokenStorage.getUsername()===null){
          clearInterval(this.interval);
        } else {
          this.userService.getFightsByUser(this.tokenStorage.getUsername()).subscribe(res => {
            if (res.filter(fight => fight.fightStatus === 'IN_PROGRESS').length>0) {
              sessionStorage.setItem("fightStatus", "IN_PROGRESS");
              if(this.router.url.toString().indexOf('fight')===-1){
                this.router.navigate(['fight']);
              }
            } else {
              sessionStorage.setItem("fightStatus", "");
            }
          });
        }
      },2000);
      this.roles = this.tokenStorage.getAuthorities();
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

  ngOnDestroy(): void {
    clearInterval(this.interval);
  }

}
