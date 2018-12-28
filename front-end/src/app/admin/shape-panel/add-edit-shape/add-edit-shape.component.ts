import { Component, OnInit } from '@angular/core';
import {ShapeService} from "../../../services/shape.service";
import {ActivatedRoute, Params, Router} from "@angular/router";

@Component({
  selector: 'app-add-edit-shape',
  templateUrl: './add-edit-shape.component.html',
  styleUrls: ['./add-edit-shape.component.css']
})
export class AddEditShapeComponent implements OnInit {

  constructor(private service: ShapeService, private router:Router,private activatedRoute: ActivatedRoute) {
  // subscribe to router event
  this.activatedRoute.queryParams.subscribe((params: Params) => {
  console.log(params);
  });

  }

  ngOnInit() {
    console.log(this.router);
  }

}
