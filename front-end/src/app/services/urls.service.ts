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
  allColors=this.host+"/colormap/all";
  saveColor=this.host+"/colormap/save";
  colorById=this.host+"/colormap/";
  sampleShape=this.host+"/shapes/sample";
  allSkillStatusEffects=this.host+"/skill/effects";
  allTargetTypes=this.host+"/skill/targets";
  allValueModifierTypes=this.host+"/skill/modifiers";
  allShapes=this.host+"/shapes/all";
  saveShape=this.host+"/shapes/save";
  shapeById=this.host+"/shapes/";
  lastMaintenanceMessage=this.host+"/maintenance-log/retrieve";
  allThresholds=this.host+"/thresholds/all";
  saveThreshold=this.host+"/thresholds/save";
  friends = this.host+"/player/friends/";
  messagesByCallers = this.host+"/message/get";
  messageSave = this.host+"/message/save";
  challenge=this.host+"/fight/save";
  fightersByUser=this.host+"/fighters/user/";
  saveFighter=this.host+"/fighters/save";
  constructor() { }
}
