import { Component, OnInit } from '@angular/core';
import {ShapeService} from "../../../services/shape.service";
import {ActivatedRoute, Params, Route, Router} from "@angular/router";
import {SkillsService} from "../../../services/skills.service";
import {Skill} from "../../../dtos/skill";

@Component({
  selector: 'app-add-edit-skill',
  templateUrl: './add-edit-skill.component.html',
  styleUrls: ['./add-edit-skill.component.css']
})
export class AddEditSkillComponent implements OnInit {
  form: Skill={};


  constructor(private service: SkillsService, private router:Router,private activatedRoute: ActivatedRoute) {
    // subscribe to router event
    this.activatedRoute.queryParams.subscribe(() => {
      this.service.getSkillById(parseInt(this.activatedRoute.snapshot.paramMap.get('id'))).subscribe(res=>{
        this.form=res;
      })
    });

  }

  ngOnInit() {
  }
  onSubmit(){
    this.service.saveSkill(this.form).subscribe(res=>{
      this.form=res;
      this.router.navigate(['/admin/skills']);
    })
  }
}
