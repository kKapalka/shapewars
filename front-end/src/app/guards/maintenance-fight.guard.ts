import { Injectable } from '@angular/core';
import {CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router, ActivatedRoute} from '@angular/router';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class MaintenanceFightGuard implements CanActivate {
  constructor(private router:Router, private route:ActivatedRoute){}
  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {

    console.log(sessionStorage.getItem("maintenanceMessageType"));
    console.log(sessionStorage.getItem("fightStatus"));
    if(sessionStorage.getItem("maintenanceMessageType")!=='WORKING'){
      this.router.navigate(["error"]);
      return false;
    }
    else{
      if(sessionStorage.getItem("fightStatus")==='IN_PROGRESS') {
        this.router.navigate(["fight"]);
        return false;
      }
      return true;
    }
  }
}
