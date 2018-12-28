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
  targetTypes:string[]=[];
  valueModTypes:string[]=[];
  statusEffects:string[]=[];
  dataSources:MatTableDataSource<SkillEffect>[] = [];
  constructor(private service: SkillsService, private router:Router,private activatedRoute: ActivatedRoute) { }

  ngOnInit() {
    this.retrieveSkillData();
    this.retrieveSkillEnums();
  }
  removeBundle(ev,index){
    ev.preventDefault();
    this.form.skillEffectBundles.splice(index,1);
  }
  onSelectFile(event) { // called each time file input changes
    if (event.target.files && event.target.files[0]) {
      let reader:FileReader = new FileReader();
      reader.readAsDataURL(event.target.files[0]); // read file as data url
      reader.onload = (event) => { // called once readAsDataURL is completed
        // @ts-ignore result z event.target ponoć nie istnieje
        this.form.icon = event.target.result;
        console.log(event.target);
      }
    }
  }
  retrieveSkillEnums() {
    this.service.getAllSkillStatusEffects().subscribe((res) => {
      this.statusEffects = res;
    });
    this.service.getAllTargetTypes().subscribe((res) => {
      this.targetTypes = res;
    });
    this.service.getAllValueModifierTypes().subscribe((res) => {
      this.valueModTypes = res;
    });
  }
  retrieveSkillData(){
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
