import { Component, OnInit } from '@angular/core';
import {ShapeService} from "../../../services/shape.service";
import {ActivatedRoute, Params, Route, Router} from "@angular/router";
import {SkillsService} from "../../../services/skills.service";
import {Skill} from "../../../dtos/skill";
import {e} from "@angular/core/src/render3";
import {SkillEffectBundle} from "../../../dtos/skillEffectBundle";
import {MatTableDataSource} from "@angular/material";
import {SkillEffect} from "../../../dtos/skillEffect";

@Component({
  selector: 'app-add-edit-skill',
  templateUrl: './add-edit-skill.component.html',
  styleUrls: ['./add-edit-skill.component.css']
})
export class AddEditSkillComponent implements OnInit {
  form: Skill={};
  dataSources:MatTableDataSource<SkillEffect>[] = [];
  constructor(private service: SkillsService, private router:Router,private activatedRoute: ActivatedRoute) {
    // subscribe to router event
    this.activatedRoute.queryParams.subscribe(() => {
      this.service.getSkillById(parseInt(this.activatedRoute.snapshot.paramMap.get('id'))).subscribe(res=>{
        this.form=res;
        this.form.skillEffectBundles.forEach(bundle=>{
          this.dataSources.push(new MatTableDataSource(bundle.skillEffectDtos));
        });
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
  test($event,row){
    $event.preventDefault();
    console.log(row);
  }
  addNewEffect(event,bundle:SkillEffectBundle){
    event.preventDefault();
    let source = this.dataSources.find(dataSource=>dataSource.data==bundle.skillEffectDtos);
    bundle.skillEffectDtos.push({
      skillStatusEffect:"",
      targetType:"",
      valueModifierType:"",
      minValue:0,
      maxValue:0
    });
    source.data=bundle.skillEffectDtos;
  }
}
