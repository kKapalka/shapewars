import {Component, ElementRef, Input, OnInit, ViewChild} from '@angular/core';
@Component({
  selector: 'app-shape-inventory',
  templateUrl: './shape-inventory.component.html',
  styleUrls: ['./shape-inventory.component.css']
})
export class ShapeInventoryComponent implements OnInit {
  @Input()
  fighters: any;
  constructor() {
  }

  ngOnInit() {

  }
  getPartyFighters(){
    return this.fighters.filter(fighter=>fighter.slot!=='INVENTORY');
  }
  getInventoryFighters() {
    return this.fighters.filter(fighter => fighter.slot === 'INVENTORY');
  }
}

