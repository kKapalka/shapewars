import { Component, OnInit } from '@angular/core';
import {UserService} from "../../services/user.service";
import {TokenStorageService} from "../../auth/token-storage.service";

@Component({
  selector: 'app-user-panel',
  templateUrl: './user-panel.component.html',
  styleUrls: ['./user-panel.component.css']
})
export class UserPanelComponent implements OnInit {
  users:any[];
  constructor(private service:UserService, private token:TokenStorageService) { }

  ngOnInit() {
    this.service.getAllPlayers().subscribe(res=>{
      this.users=res;
      console.log(res);
    })
  }
  resetFighters(login:string){
    this.service.resetFighters(login).subscribe(res=>{
      console.log(res);
    })
  }
  toggleAdminPrivileges(user:any){
    let dto={
      selectUsername:user.login,
      admin:user.admin,
      issuerToken:this.token.getToken()
    };
    this.service.setPrivileges(dto).subscribe(res=>{
      console.log(res);
    })
  }
  ban(login:string){
    this.service.ban(login,this.token.getToken()).subscribe(res=>{
      console.log(res);
    })
  }
}
