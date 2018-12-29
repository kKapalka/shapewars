import { Component, OnInit } from '@angular/core';
import {ShapeService} from "../../services/shape.service";
import {SkillsService} from "../../services/skills.service";

@Component({
  selector: 'app-shape-panel',
  templateUrl: './shape-panel.component.html',
  styleUrls: ['./shape-panel.component.css']
})
export class ShapePanelComponent implements OnInit {

  shapes$;
  skills;
  constructor(private service: ShapeService, private skillService:SkillsService) {
    this.service.getAllShapes().subscribe(res=>{
      this.shapes$=res;
    });
    this.skillService.getAllSkills().subscribe(res=>{
      this.skills=res;
    })
  }
  filterSkillsWithIds(ids:number[]){
    return this.skills.filter(skill=>ids.includes(skill.id));
  }
  ngOnInit() {
  }

}
