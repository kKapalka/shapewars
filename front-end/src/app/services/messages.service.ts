import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {UrlsService} from "./urls.service";
import {Observable} from "rxjs";
import Message from "../dtos/message";

@Injectable({
  providedIn: 'root'
})
export class MessagesService {

  constructor(private http: HttpClient, private urls: UrlsService) { }

  getMessagesByCallers(callers:string[]):Observable<any> {
    return this.http.post(this.urls.messagesByCallers, {callers: callers}, {responseType: 'json'});
  }

  sendMessage(message:Message):Observable<any>{
    return this.http.post(this.urls.messageSave,message,{responseType: 'json'});
  }

}
