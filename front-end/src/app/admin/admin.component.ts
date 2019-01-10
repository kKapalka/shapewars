import { Component, OnInit } from '@angular/core';
import { UserService } from '../services/user.service';
import {TokenStorageService} from "../auth/token-storage.service";
import {MaintenanceService} from "../services/maintenance.service";
import MaintenanceMessage from "../dtos/maintenanceMessage";

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {
  info: any;
  form:MaintenanceMessage={};
  messageTypes:string[];
  constructor(private userService: UserService, private token: TokenStorageService,
    private maintenanceService:MaintenanceService) { }

  ngOnInit() {
    this.maintenanceService.getMessageTypes().subscribe(res=>{
      this.messageTypes=res;
    });
  }
  onSubmit(){
    this.form.informerName=this.token.getUsername();
    this.maintenanceService.saveMaintenanceMessage(this.form).subscribe(res=>{
      console.log(res);
    })
  }
}
