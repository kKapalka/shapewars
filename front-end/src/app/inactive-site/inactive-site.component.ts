import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-inactive-site',
  templateUrl: './inactive-site.component.html',
  styleUrls: ['./inactive-site.component.css']
})
export class InactiveSiteComponent implements OnInit {

  constructor() { }

  ngOnInit() {
    let lastMessageType = localStorage.getItem("LastLogType");
    if(lastMessageType==="WORKING"){
      window.location.href = "/";
    }
  }

}
