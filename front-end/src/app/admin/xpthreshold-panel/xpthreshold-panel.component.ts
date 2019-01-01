import { Component, OnInit } from '@angular/core';
import Threshold from "../../dtos/threshold";
import {ThresholdService} from "../../services/threshold.service";
import {MatTableDataSource} from "@angular/material";

@Component({
  selector: 'app-xpthreshold-panel',
  templateUrl: './xpthreshold-panel.component.html',
  styleUrls: ['./xpthreshold-panel.component.css']
})
export class XpthresholdPanelComponent implements OnInit {
  thresholds:MatTableDataSource<Threshold>;
  data:Threshold[];
  constructor(private service:ThresholdService) { }

  ngOnInit() {
    this.service.getAllThresholds().subscribe(res=>{
      this.thresholds=new MatTableDataSource<Threshold>(res);
      this.data=res;
    })
  }
  save($event,el){
    $event.preventDefault();
    this.service.saveThreshold(el).subscribe(res=>{
      console.log(res);
    });
  }
  remove($event,el){
    $event.preventDefault();
    console.log(el);
  }
  addNewThreshold($event){
    $event.preventDefault();
    this.data.push({
      level:0,
      threshold:0
    });
    this.thresholds=new MatTableDataSource<Threshold>(this.data);
  }
}
