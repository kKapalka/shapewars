import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {UrlsService} from "./urls.service";
import {Observable} from "rxjs";
import {Skill} from "../dtos/skill";

@Injectable({
  providedIn: 'root'
})
export class SkillsService {

  constructor(private http: HttpClient, private urls: UrlsService) { }

  getAllSkills(): Observable<object> {
    return this.http.get(this.urls.allSkills, { responseType: 'json' });
  }
  getSkillById(id:number):Observable<any>{
    return this.http.get(this.urls.skillById+id,{responseType:'json'});
  }
  saveSkill(skill:Skill):Observable<any>{
    return this.http.post(this.urls.saveSkill,skill,{responseType:'json'})
  }
  deleteSkillById(id:number):Observable<any>{
    return this.http.delete(this.urls.skillById+id,{responseType:'json'})
  }
  getAllSkillStatusEffects():Observable<any>{
    return this.http.get(this.urls.allSkillStatusEffects,{responseType:'json'})
  }
  getAllTargetTypes():Observable<any>{
    return this.http.get(this.urls.allTargetTypes,{responseType:'json'})
  }
  getAllValueModifierTypes():Observable<any>{
    return this.http.get(this.urls.allValueModifierTypes,{responseType:'json'})
  }
}
