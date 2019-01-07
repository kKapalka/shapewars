import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {UrlsService} from "./urls.service";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class MaintenanceService {

  constructor(private http: HttpClient, private urls: UrlsService) { }

  getLastMaintenanceLogMessage(): Observable<any> {
    return this.http.get(this.urls.lastMaintenanceMessage,{responseType:'json'});
  }
  getMessageTypes(): Observable<any> {
    return this.http.get(this.urls.allMessageTypes,{responseType:'json'});
  }
  saveMaintenanceMessage(message:any): Observable<any> {
    return this.http.post(this.urls.saveMaintenanceMessage,message,{responseType:'json'});
  }
}
