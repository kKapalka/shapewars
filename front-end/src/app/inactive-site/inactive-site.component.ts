import { Component, OnInit } from '@angular/core';
import {MaintenanceService} from "../services/maintenance.service";
import MaintenanceMessage from "../dtos/maintenanceMessage";

@Component({
  selector: 'app-inactive-site',
  templateUrl: './inactive-site.component.html',
  styleUrls: ['./inactive-site.component.css']
})
export class InactiveSiteComponent implements OnInit {
  message:MaintenanceMessage={};
  constructor(private service:MaintenanceService) { }

  ngOnInit() {

    this.service.getLastMaintenanceLogMessage().subscribe(res=>{
      this.message=res;
    })
  }

}
