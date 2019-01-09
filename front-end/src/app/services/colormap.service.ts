import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {UrlsService} from "./urls.service";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class ColormapService {

  constructor(private http: HttpClient, private urls: UrlsService) { }

  getAllColors(): Observable<any> {
    return this.http.get(this.urls.allColors, { responseType: 'json' });
  }
  getColorById(id:number):Observable<any>{
    return this.http.get(this.urls.colorById+id,{responseType:'json'});
  }
  saveColor(colormap:any):Observable<any>{
    return this.http.post(this.urls.saveColor,colormap,{responseType:'json'})
  }
  getSampleShapeIcon():Observable<any>{
    return this.http.get(this.urls.sampleShape, {responseType:'text'});
  }
}
