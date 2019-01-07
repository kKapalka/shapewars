import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-fighter-details',
  templateUrl: './fighter-details.component.html',
  styleUrls: ['./fighter-details.component.css']
})
export class FighterDetailsComponent implements OnInit {
  @Input()
  fighter:any;
  constructor() { }

  ngOnInit() {
  }

}
