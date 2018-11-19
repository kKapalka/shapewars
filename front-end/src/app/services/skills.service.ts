import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {UrlsService} from "./urls.service";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class SkillsService {

  constructor(private http: HttpClient, private urls: UrlsService) { }

  getAllSkills(): Observable<object> {
    return this.http.get(this.urls.allSkills, { responseType: 'json' });
  }

}
