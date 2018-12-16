import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {UrlsService} from "./urls.service";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class MaintenanceService {

  constructor(private http: HttpClient, private urls: UrlsService) { }

  getLastMaintenanceLogMessage(): Observable<object> {
    return this.http.get(this.urls.lastMaintenanceMessage);
  }
}
