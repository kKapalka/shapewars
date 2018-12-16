import { Component, OnInit } from '@angular/core';
import { TokenStorageService } from './auth/token-storage.service';
import {MaintenanceService} from "./services/maintenance.service";
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
  constructor(private tokenStorage: TokenStorageService, private maintenanceService:MaintenanceService) { }

  ngOnInit() {
    this.maintenanceService.getLastMaintenanceLogMessage().subscribe(res=>{
      this.lastLogMessage = res;
      this.lastLogMessageType = this.lastLogMessage.messageType;
      localStorage.setItem("LastLogType",this.lastLogMessageType);
    });
    if (this.tokenStorage.getToken()) {
      this.roles = this.tokenStorage.getAuthorities();
      this.roles.every(role => {
        if (role === 'ROLE_ADMIN') {
          this.authority = 'admin';
          return false;
        } else if (role === 'ROLE_PM') {
          this.authority = 'pm';
          return false;
        }
        this.authority = 'user';
        return true;
      });
    }
  }

  onLogout(event){
    event.preventDefault();
    this.tokenStorage.signOut();
    window.location.pathname="/home";
  }
}
