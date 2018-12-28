import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class UrlsService {
  host="http://localhost:7070";
  playerData=this.host+"/player/";
  allSkills=this.host+"/skill/all";
  saveSkill=this.host+"/skill/save";
  skillById=this.host+"/skill/";
  allSkillStatusEffects=this.host+"/skill/effects";
  allTargetTypes=this.host+"/skill/targets";
  allValueModifierTypes=this.host+"/skill/modifiers";
  allShapes=this.host+"/shapes/all";
  saveShape=this.host+"/shapes/save";
  shapeById=this.host+"/shapes/";
  lastMaintenanceMessage=this.host+"/maintenance-log/retrieve";
  constructor() { }
}
