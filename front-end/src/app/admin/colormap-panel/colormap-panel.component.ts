import { Component, OnInit } from '@angular/core';
import {SkillsService} from "../../services/skills.service";
import {ColormapService} from "../../services/colormap.service";

@Component({
  selector: 'app-colormap-panel',
  templateUrl: './colormap-panel.component.html',
  styleUrls: ['./colormap-panel.component.css']
})
export class ColormapPanelComponent implements OnInit {

  colors;
  constructor(private service: ColormapService) {
    this.service.getAllColors().subscribe(res=>{
      this.colors=res;
    })
  }

  ngOnInit() {
  }

}
