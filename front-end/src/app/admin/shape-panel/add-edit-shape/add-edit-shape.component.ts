import { Component, OnInit } from '@angular/core';
import {ShapeService} from "../../../services/shape.service";
import {ActivatedRoute, Params, Router} from "@angular/router";
import {MatTableDataSource} from "@angular/material";
import {Shape} from "../../../dtos/shape";
import {SkillsService} from "../../../services/skills.service";
import {Skill} from "../../../dtos/skill";

@Component({
  selector: 'app-add-edit-shape',
  templateUrl: './add-edit-shape.component.html',
  styleUrls: ['./add-edit-shape.component.css']
})
export class AddEditShapeComponent implements OnInit {
  form:Shape;
  skills:any;
  constructor(private service: ShapeService, private router:Router,
              private activatedRoute: ActivatedRoute, private skillService:SkillsService) {
  }

  ngOnInit() {
    this.retrieveShapeData();
    this.retrieveSkills();
  }
  retrieveSkills(){
    this.skillService.getAllSkills().subscribe(res=>{
      this.skills=res;
    })
  }
  retrieveShapeData(){
    let shapeId:number=parseInt(this.activatedRoute.snapshot.paramMap.get('id'));
    if(!isNaN(shapeId)) {
      this.activatedRoute.queryParams.subscribe(() => {
        this.service.getShapeById(shapeId).subscribe(res => {
          this.form = res;
        })
      });
    } else{
      this.form = {
        name:"",
        skillIDset:[],
        hpParameters:[],
        strParameters:[],
        armParameters:[],
        speed:0,
        image:""
      }
    }
  }
  onSubmit(){
    if(this.form.skillIDset.length!=4){
      alert("Shape must have access to exactly 4 skills!");
    } else{
      this.service.saveShape(this.form).subscribe(res=>{
        this.form=res;
        this.router.navigate(['/admin/shapes']);
      })
    }

  }
  onSelectFile(event) { // called each time file input changes
    if (event.target.files && event.target.files[0]) {
      let reader:FileReader = new FileReader();
      reader.readAsDataURL(event.target.files[0]); // read file as data url
      reader.onload = (event) => { // called once readAsDataURL is completed
        // @ts-ignore result z event.target ponoć nie istnieje
        this.form.image = event.target.result;
        console.log(event.target);
      }
    }
  }
}
