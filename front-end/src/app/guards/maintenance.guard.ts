import { Injectable } from '@angular/core';
import {CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router, ActivatedRoute} from '@angular/router';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class MaintenanceGuard implements CanActivate {
  constructor(private router:Router, private route:ActivatedRoute){}
  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {

    if(sessionStorage.getItem("maintenanceMessageType")!=='WORKING'){
      this.router.navigate(["error"]);
      return false;
    }
    else{
      return true;
    }
  }
}
