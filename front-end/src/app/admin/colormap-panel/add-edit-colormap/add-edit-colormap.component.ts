import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import ColorMap from "../../../dtos/colorMap";
import {ColormapService} from "../../../services/colormap.service";

@Component({
  selector: 'app-add-edit-colormap',
  templateUrl: './add-edit-colormap.component.html',
  styleUrls: ['./add-edit-colormap.component.css']
})
export class AddEditColormapComponent implements OnInit {

  form:ColorMap;
  skills:any;
  constructor(private service: ColormapService, private router:Router,
              private activatedRoute: ActivatedRoute) {
  }

  ngOnInit() {
    this.retrieveColorData();
  }
  retrieveColorData(){
    let colorId:number=parseInt(this.activatedRoute.snapshot.paramMap.get('id'));
    if(!isNaN(colorId)) {
      this.activatedRoute.queryParams.subscribe(() => {
        this.service.getColorById(colorId).subscribe(res => {
          this.form = res;
        })
      });
    } else{
      this.form = {
        colorName:"",
        colorMap:""
      }
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
        console.log(event.target);
      }
    }
  }
}
