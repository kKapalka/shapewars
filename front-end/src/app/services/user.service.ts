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
  findFriendsByUsername(login:string): Observable<object>{
    return this.http.get(this.urls.friends+login, { responseType: 'json' });
  }
  getFightersByUser(login:string): Observable<any>{
    return this.http.get(this.urls.fightersByUser+login,{ responseType: 'json' })
  }
  saveFighter(fighter:any):Observable<any>{
    return this.http.post(this.urls.saveFighter,fighter,{responseType:'json'})
  }
}
