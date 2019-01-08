import {Component, Input, OnInit} from '@angular/core';
import {UserService} from "../../services/user.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-main-menu',
  templateUrl: './main-menu.component.html',
  styleUrls: ['./main-menu.component.css']
})
export class MainMenuComponent implements OnInit {
  @Input()
  info;
  searchUsername:string;
  friends:any;
  changelog:any;
  constructor(private service:UserService, private router:Router) { }

  ngOnInit() {
    console.log(this.info);
    this.service.findFriendsByUsername(this.info.username).subscribe(res=>{
      this.friends=res;
    })
    this.service.getChangelog().subscribe((res=>{
      this.changelog=res;
      console.log(res);
    }))
  }
  search(){
    this.service.getPlayerData(this.searchUsername).subscribe(()=>{
      this.router.navigate(["user",this.searchUsername]);
    },console.log);
    console.log(this.searchUsername);
  }
}
