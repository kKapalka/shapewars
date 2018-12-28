import { Injectable } from '@angular/core';
import {Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {UrlsService} from "./urls.service";
import {Skill} from "../dtos/skill";
import {Shape} from "../dtos/shape";

@Injectable({
  providedIn: 'root'
})
export class ShapeService {

  constructor(private http: HttpClient, private urls: UrlsService) { }

  getAllShapes(): Observable<object> {
    return this.http.get(this.urls.allShapes, { responseType: 'json' });
  }
  getShapeById(id:number):Observable<any>{
    return this.http.get(this.urls.shapeById+id,{responseType:'json'});
  }
  saveShape(shape:Shape):Observable<any>{
    return this.http.post(this.urls.saveShape,shape,{responseType:'json'})
  }
}
