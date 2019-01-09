import { Injectable } from '@angular/core';
import {CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router} from '@angular/router';
import { Observable } from 'rxjs';
import {MaintenanceService} from "../services/maintenance.service";
import MaintenanceMessage from "../dtos/maintenanceMessage";

@Injectable({
  providedIn: 'root'
})
export class MaintenanceReverseGuard implements CanActivate {
  constructor(private service:MaintenanceService,private router:Router){}
  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {
    let message:MaintenanceMessage={};
    this.service.getLastMaintenanceLogMessage().subscribe(res=>{
      message=res;
    });
    if(message.messageType!==undefined && message.messageType!=="WORKING"){
      return true;
    } else{
      this.router.navigate(["home"]);
      return false;
    }
  }
}
