import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {SkillsService} from "../../../services/skills.service";
import {Skill} from "../../../dtos/skill";
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
    let skillId:number=parseInt(this.activatedRoute.snapshot.paramMap.get('id'));
    if(!isNaN(skillId)) {
      this.activatedRoute.queryParams.subscribe(() => {
        this.service.getSkillById(skillId).subscribe(res => {
          this.form = res;
          this.form.skillEffectBundles.forEach(bundle => {
            this.dataSources.push(new MatTableDataSource(bundle.skillEffectDtos));
          });
        })
      });
    } else{
      this.form = {
        name:"",
        icon:"",
        tooltip:"",
        skillEffectBundles:[],
        cost:0
      }
    }

  }

  ngOnInit() {
  }
  onSubmit(){
    this.service.saveSkill(this.form).subscribe(res=>{
      this.form=res;
      this.router.navigate(['/admin/skills']);
    })
  }
  remove(event,bundle:SkillEffectBundle,row){
    event.preventDefault();
    let source = this.dataSources.find(dataSource=>dataSource.data==bundle.skillEffectDtos);
    bundle.skillEffectDtos.splice(bundle.skillEffectDtos.indexOf(row),1);
    source.data=bundle.skillEffectDtos;
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
  addEffectBundle(event){
    event.preventDefault();
    this.form.skillEffectBundles.push({
      accuracy:0,
      skillEffectDtos:[
        {
          skillStatusEffect:"",
          targetType:"",
          valueModifierType:"",
          minValue:0,
          maxValue:0
        }
      ]
    });
    this.dataSources = this.form.skillEffectBundles.map(bundle=>new MatTableDataSource(bundle.skillEffectDtos));
    console.log(this.dataSources);
  }
}
