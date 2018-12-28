import {SkillEffectBundle} from "./skillEffectBundle";

export class Skill {
  id?:number;
  name?:string;
  icon?:string;
  tooltip?:string;
  cost?:number;
  skillEffectBundles?:SkillEffectBundle[];
}
