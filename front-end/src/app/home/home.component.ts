import { Component, OnInit } from '@angular/core';

import { TokenStorageService } from '../auth/token-storage.service';
import {Router} from "@angular/router";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  info: any;

  constructor(private token: TokenStorageService, private router:Router) { }

  ngOnInit() {
    if(sessionStorage.getItem("fightStatus")==='IN_PROGRESS'){
      if(this.router.url.toString().indexOf('fight')===-1){
        this.router.navigate(['fight']);
      }
    }
    this.info = {
      token: this.token.getToken(),
      username: this.token.getUsername(),
      authorities: this.token.getAuthorities()
    };

  }

  logout() {
    this.token.signOut();
    window.location.reload();
  }
}
