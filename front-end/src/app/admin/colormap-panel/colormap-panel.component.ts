import { Component, OnInit } from '@angular/core';
import {ColormapService} from "../../services/colormap.service";
import ColorMap from "../../dtos/colorMap";

@Component({
  selector: 'app-colormap-panel',
  templateUrl: './colormap-panel.component.html',
  styleUrls: ['./colormap-panel.component.css']
})
export class ColormapPanelComponent implements OnInit {

  colors:ColorMap[]=[];
  constructor(private service: ColormapService) {
    this.service.getAllColors().subscribe(res=>{
      this.colors=res;
    })
  }
  composeEffect(color:ColorMap){
    if(color.colorDamageDtoList.length!==0) {
      return "Deals " + color.colorDamageDtoList.sort((a,b)=>{
        return a.id-b.id;
      }).map
      (color => color.damagePercentage + "% to " + color.enemyColorName);
    } else{
      return "Needs to be configured";
    }
  }
  ngOnInit() {
  }

}
