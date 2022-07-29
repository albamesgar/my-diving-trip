import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Club } from '../models/club.model';
import { Dive } from '../models/dive.model';
import { DiveBook } from '../models/diveBook.model';
import { Passport } from '../models/passport.model';
import { Titulation } from '../models/titulation.model';
import { User } from '../models/user.model';

@Injectable({
  providedIn: 'root'
})
export class EdgeService {

  private readonly BASE_URL='http://localhost:8080';
  constructor(
    private http : HttpClient
  ) {}

  //Users
  getAllUsernames(): Observable<any> {
    return this.http.get(`${this.BASE_URL}/users/usernames`);
  }

  isAuthenticated(): boolean {
    const token: string | null = localStorage.getItem('currentUser');
    return token !== null;
  }

  registerUser(username: string, password: string, firstName: string, lastName: string, birthDate: Date, 
    email: string, profilePicture: string): Observable<User> {
    const user: User = new User(
      null,
      username,
      password,
      firstName,
      lastName,
      birthDate,
      email,
      profilePicture,
      "DIVER"
    );

    return this.http.post<User>(`${this.BASE_URL}/users`, user);       
  }

  login(username: string, password: string): Observable<User> {
    let headers = new HttpHeaders();
    headers = headers.append('Authorization', 'Basic ' + btoa(`${username}:${password}`));

    return this.http.get<User>(`${this.BASE_URL}/login`, { headers: headers });  
  }

  logout(): void {
      // Remove user from local storage to log user out
      localStorage.removeItem('currentUser');
  }

  modifyUser(userId: number, username: string, password: string, firstName: string, lastName: string, birthDate: Date, 
    email: string, profilePicture: string): Observable<User> {
    const user: User = new User(
      null,
      username,
      password,
      firstName,
      lastName,
      birthDate,
      email,
      profilePicture,
      "DIVER"
    );

    return this.http.put<User>(`${this.BASE_URL}/users/${userId}`, user);
  }

  deleteUser(userId: number): any {
    return this.http.delete(`${this.BASE_URL}/users/${userId}`);
  }

  //Dive Book
  getDiveBook(id: number): Observable<any>{
    return this.http.get<DiveBook>(`${this.BASE_URL}/dive-books/${id}`);
  }

  addDiveToDiveBook(diveBookId: number, dive: Dive): Observable<Dive>{
    return this.http.post<Dive>(`${this.BASE_URL}/add-dive/dive-book/${diveBookId}`, dive);
  }

  modifyDiveOfDiveBook(diveId: number, dive: Dive): any{
    return this.http.put(`${this.BASE_URL}/modify-dive/${diveId}`, dive);
  }

  //Passport
  getPassport(id: number): Observable<any>{
    return this.http.get<Passport>(`${this.BASE_URL}/passports/${id}`);
  }

  addTitulation(passportId: number, titulation: Titulation): Observable<Titulation>{
    return this.http.post<Titulation>(`${this.BASE_URL}/add-titulation/passport/${passportId}`, titulation);
  }

  modifyTitulation(titulationId: number, titulation: Titulation): void{
    this.http.put(`${this.BASE_URL}/modify-titulation/${titulationId}`, titulation);
  }

  //Club
  getAllClubs(): Observable<any>{
    return this.http.get<Club[]>(`${this.BASE_URL}/clubs`);
  }
  
  getClubById(id: number): Observable<Club>{
    return this.http.get<Club>(`${this.BASE_URL}/clubs/${id}`);
  }
}
