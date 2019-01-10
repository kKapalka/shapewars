import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import ColorMap from "../../../dtos/colorMap";
import {ColormapService} from "../../../services/colormap.service";
import ColorDamage from "../../../dtos/colorDamage";

@Component({
  selector: 'app-add-edit-colormap',
  templateUrl: './add-edit-colormap.component.html',
  styleUrls: ['./add-edit-colormap.component.css']
})
export class AddEditColormapComponent implements OnInit {

  form: ColorMap={};
  allOtherColors: ColorMap[]=[];
  sampleShape: string="";
  canvas: HTMLCanvasElement;

  constructor(private service: ColormapService, private router: Router,
              private activatedRoute: ActivatedRoute) {
  }

  ngOnInit() {
    this.retrieveColorData();
    setTimeout(() => {
      this.getSampleShapeIcon();
    }, 1000);
    // @ts-ignore
    this.canvas = document.getElementById("iconCanvas");
  }

  retrieveColorData() {
    let colorId: number = parseInt(this.activatedRoute.snapshot.paramMap.get('id'));
    this.service.getAllColors().subscribe((res) => {
      this.allOtherColors = res;
    });
    if (!isNaN(colorId)) {
      setTimeout(()=>{
        this.activatedRoute.queryParams.subscribe(() => {
          this.service.getColorById(colorId).subscribe(res => {
            this.allOtherColors = this.allOtherColors.filter(color => color.colorName != res.colorName);
            this.form = res;
            let absentColors=this.allOtherColors
              .filter(color=>!this.form.colorDamageDtoList.map
              (presentColor=>presentColor.enemyColorName).includes(color.colorName));
            let absentColorDamageList=absentColors
              .map(color => ({
                colorName: this.form.colorName,
                enemyColorName: color.colorName,
                damagePercentage: 100
              }));
            if(this.form.colorDamageDtoList.length==0){
              this.form.colorDamageDtoList=absentColorDamageList;
            } else{
              this.form.colorDamageDtoList=this.form.colorDamageDtoList.concat(absentColorDamageList);
            }
          })
        });
      },500);
    } else {
      setTimeout(() => {
        this.form = {
          colorName: "",
          colorMap: "",
          colorDamageDtoList: this.allOtherColors.map(color => ({
            colorName: this.form.colorName,
            enemyColorName: color.colorName,
            damagePercentage: 100
          }))
        }
      },500);
    }
  }
  getSampleShapeIcon(){
    this.service.getSampleShapeIcon().subscribe(res=>{
      this.sampleShape=res;
      this.refreshCanvas();
    })
  }
  refreshCanvas(){
    let ctx=this.canvas.getContext("2d");
    ctx.clearRect(0,0,this.canvas.width,this.canvas.height);
    let bgImage=new Image();
    bgImage.src=this.form.colorMap;
    bgImage.onload=function(){
      ctx.drawImage(bgImage,0,0);
    }

    ctx.globalCompositeOperation = 'destination-atop';
    let iconImg=new Image();
    iconImg.src=this.sampleShape;
    iconImg.onload=function(){
      ctx.drawImage(iconImg,0,0);
    }
  }
  onSubmit(){
    this.service.saveColor(this.form).subscribe(res=>{
      this.form=res;
      this.router.navigate(['/admin/colors']);
    })
  }
  onSelectFile(event) { // called each time file input changes
    if (event.target.files && event.target.files[0]) {
      let reader:FileReader = new FileReader();
      reader.readAsDataURL(event.target.files[0]); // read file as data url
      reader.onload = (event) => { // called once readAsDataURL is completed
        // @ts-ignore result z event.target ponoć nie istnieje
        this.form.colorMap = event.target.result;
        this.refreshCanvas();
      }
    }
  }
}
