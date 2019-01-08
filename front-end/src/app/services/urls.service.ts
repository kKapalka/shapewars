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
  allMessageTypes=this.host+"/maintenance-log/message-types";
  saveMaintenanceMessage=this.host+"/maintenance-log/save";
  allThresholds=this.host+"/thresholds/all";
  saveThreshold=this.host+"/thresholds/save";
  friends = this.host+"/player/friends/";
  allPlayers = this.host+"/player/all";
  setPrivileges = this.host+"/auth/privileges";
  resetFighters = this.host+"/auth/reset-fighters/";
  ban = this.host+"/auth/ban/";
  messagesByCallers = this.host+"/message/get";
  messageSave = this.host+"/message/save";
  challenge=this.host+"/fight/save";
  fightersByUser=this.host+"/fighters/user/";
  saveFighter=this.host+"/fighters/save";
  changelog=this.host+"/changelog/all";
  constructor() { }
}
