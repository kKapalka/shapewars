import { Component, OnInit } from '@angular/core';
import {SkillsService} from "../../services/skills.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-skill-panel',
  templateUrl: './skill-panel.component.html',
  styleUrls: ['./skill-panel.component.css']
})
export class SkillPanelComponent implements OnInit {

  skills$;
  constructor(private service: SkillsService) {
    this.service.getAllSkills().subscribe(res=>{
      this.skills$=res;
    })
  }

  ngOnInit() {

  }
  editSkill(e:Event, id:number){
    window.location.href="admin/skills/"+id;
  }

}
