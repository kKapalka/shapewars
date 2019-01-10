import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import {UrlsService} from "./urls.service";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient, private urls: UrlsService) { }

  getPlayerData(login:string): Observable<any> {
    return this.http.get(this.urls.playerData+login, { responseType: 'json' });
  }
  findFriendsByUsername(login:string): Observable<any>{
    return this.http.get(this.urls.friends+login, { responseType: 'json' });
  }
  getFightersByUser(login:string): Observable<any>{
    return this.http.get(this.urls.fightersByUser+login,{ responseType: 'json' })
  }
  getCombatantsByUser(login:string): Observable<any>{
    return this.http.get(this.urls.combatantsByUser+login,{ responseType: 'json' })
  }
  saveFighter(fighter:any):Observable<any>{
    return this.http.post(this.urls.saveFighter,fighter,{responseType:'json'})
  }
  getChangelog():Observable<any>{
    return this.http.get(this.urls.changelog,{responseType:'json'})
  }
  getAllPlayers():Observable<any>{
    return this.http.get(this.urls.allPlayers,{responseType:'json'})
  }
  setPrivileges(dto:any):Observable<any>{
    return this.http.post(this.urls.setPrivileges,dto,{responseType:'json'})
  }
  resetFighters(login:string):Observable<any>{
    return this.http.post(this.urls.resetFighters+login,{responseType:'json'})
  }
  ban(login:string,token:string):Observable<any>{
    return this.http.post(this.urls.ban+login,token,{responseType:'json'});
  }
  getFightsByUser(login:string):Observable<any>{
    return this.http.get(this.urls.fightsByUser+login,{responseType:'json'});
  }
  getChallengesForUser(login:string):Observable<any>{
    return this.http.get(this.urls.challengesForUser+login,{responseType:'json'});
  }
  getChallengesByChallenger(login:string):Observable<any>{
    return this.http.get(this.urls.challengesByChallenger+login,{responseType:'json'});
  }
}
