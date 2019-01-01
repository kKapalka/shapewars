import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {UrlsService} from "./urls.service";
import {Observable} from "rxjs";
import {Skill} from "../dtos/skill";
import Threshold from "../dtos/threshold";

@Injectable({
  providedIn: 'root'
})
export class ThresholdService {

  constructor(private http: HttpClient, private urls: UrlsService) { }

  getAllThresholds(): Observable<any> {
    return this.http.get(this.urls.allThresholds, { responseType: 'json' });
  }
  saveThreshold(threshold:Threshold):Observable<any>{
    return this.http.post(this.urls.saveThreshold,threshold,{responseType:'json'})
  }
}
