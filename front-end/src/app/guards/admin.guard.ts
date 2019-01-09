import { Injectable } from '@angular/core';
import {CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router} from '@angular/router';
import { Observable } from 'rxjs';
import {TokenStorageService} from "../auth/token-storage.service";

@Injectable({
  providedIn: 'root'
})
export class AdminGuard implements CanActivate {

  constructor(private token:TokenStorageService, public router:Router){}

  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {
    if(sessionStorage.getItem("fightStatus")==='IN_PROGRESS'){
      this.router.navigate(['fight']);
      return false;
    } else {
      if (this.token.getAuthorities().includes("ROLE_ADMIN")) {
        return true;
      } else {
        this.router.navigate(["home"]);
        return false;
      }
    }
  }
}
