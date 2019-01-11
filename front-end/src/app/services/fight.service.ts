import { Injectable } from '@angular/core';
import {Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {UrlsService} from "./urls.service";

@Injectable({
  providedIn: 'root'
})
export class FightService {

  constructor(private http: HttpClient, private urls: UrlsService) { }

  getCombatantsByUser(login:string): Observable<any>{
    return this.http.get(this.urls.combatantsByUser+login,{ responseType: 'json' })
  }
  getActionListForFight(id:number): Observable<any>{
    return this.http.get(this.urls.actionListForFight+id,{ responseType: 'json' })
  }
  getTurnOrderForFightAndTurn(fightCombatDto:any,turn:number)  : Observable<any>{
    return this.http.post(this.urls.turnOrder+turn,fightCombatDto,{ responseType: 'json' })
  }
}
