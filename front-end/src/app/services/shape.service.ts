import { Injectable } from '@angular/core';
import {Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {UrlsService} from "./urls.service";

@Injectable({
  providedIn: 'root'
})
export class ShapeService {

  constructor(private http: HttpClient, private urls: UrlsService) { }

  getAllShapes(): Observable<object> {
    return this.http.get(this.urls.allShapes, { responseType: 'json' });
  }
}
