import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import {UrlsService} from "./urls.service";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient, private urls: UrlsService) { }

  getPlayerData(login:string): Observable<object> {
    return this.http.get(this.urls.playerData+login, { responseType: 'json' });
  }
}
