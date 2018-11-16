import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private host = 'http://localhost:7070';

  private userUrl = 'http://localhost:7070/api/test/user';
  private adminUrl = 'http://localhost:7070/api/test/admin';

  constructor(private http: HttpClient) { }

  getPlayerData(login:string): Observable<object> {
    return this.http.get(this.host+"/player/"+login, { responseType: 'json' });
  }

  getAdminBoard(): Observable<string> {
    return this.http.get(this.adminUrl, { responseType: 'text' });
  }
}
