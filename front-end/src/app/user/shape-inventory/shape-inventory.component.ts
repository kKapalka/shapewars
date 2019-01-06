import {Component, ElementRef, Input, OnInit, ViewChild} from '@angular/core';
@Component({
  selector: 'app-shape-inventory',
  templateUrl: './shape-inventory.component.html',
  styleUrls: ['./shape-inventory.component.css']
})
export class ShapeInventoryComponent implements OnInit {
  @Input()
  fighters: any;
  @ViewChild('fighterCanvas')
  canvasRef:ElementRef;
  canvas:any;
  ctx:CanvasRenderingContext2D;
  dimensions:number[]=[500,600];
  fighterIcons:any;
  constructor() {
  }

  ngOnInit() {
    this.canvas=this.canvasRef.nativeElement;
    this.ctx=this.canvas.getContext('2d');
    this.ctx.clearRect(0,0,this.canvas.width,this.canvas.height);
    this.ctx.globalCompositeOperation = 'destination-atop';
    this.preparePartyIcons();
    this.plantHeaders();
    setTimeout(()=>{
      this.plantAllShapes()
    },1000);
  }
  preparePartyIcons(){
    this.fighterIcons=this.fighters.map(fighter=>({
      bg:fighter.colorMapDto.colorMap,
      shape:fighter.image,
    }))
  }
  plantHeaders(){
    let ctx=this.ctx;
    let partyText=new Image();
    partyText.src="../../../assets/party_text.png";
    partyText.onload=function(){
      ctx.drawImage(partyText,100,0);
    }
    let inventoryText=new Image();
    inventoryText.src="../../../assets/inventory_text.png";
    inventoryText.onload=function(){
      ctx.drawImage(inventoryText,100,200);
    }
  }
  plantAllShapes(){
    for(var i=0;i<this.getPartyFighters().length;i++){
      console.log(i);
      console.log(this.getPartyFighters()[i]);
      this.plantFighter(this.getPartyFighters()[i],'PARTY',i)
    }
    for(var i=0;i<this.getInventoryFighters().length;i++){

      console.log(i);
      console.log(this.getInventoryFighters()[i]);
      this.plantFighter(this.getInventoryFighters()[i],'INVENTORY',i)
    }
  }
  plantFighter(fighter,type,position){
    let ctx=this.ctx;
    let width:number=125;
    let offset=50;
    if(type==='INVENTORY'){
      offset+=200;
    }
    let background=new Image();
    background.src=fighter.fighterImage;
    background.onload=function(){
      ctx.drawImage(background,width*(position%4),offset+Math.floor(position/4),125,125);
    };
  }
  getPartyFighters(){
    return this.fighters.filter(fighter=>fighter.slot!=='INVENTORY');
  }
  getInventoryFighters() {
    return this.fighters.filter(fighter => fighter.slot === 'INVENTORY');
  }
}

