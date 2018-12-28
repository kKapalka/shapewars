import { Component, OnInit } from '@angular/core';
import {ShapeService} from "../../services/shape.service";

@Component({
  selector: 'app-shape-panel',
  templateUrl: './shape-panel.component.html',
  styleUrls: ['./shape-panel.component.css']
})
export class ShapePanelComponent implements OnInit {

  shapes$;
  constructor(private service: ShapeService) {
    this.service.getAllShapes().subscribe(res=>{
      this.shapes$=res;
    })
  }

  ngOnInit() {
  }

}
