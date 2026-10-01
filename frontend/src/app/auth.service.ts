import { Injectable, inject, signal } from '@angular/core';
import { Router } from '@angular/router';
import { ApiService } from './api.service';
import { AuthResponse, User } from './models';
@Injectable({providedIn:'root'}) export class AuthService {
 private api=inject(ApiService);private router=inject(Router); user=signal<User|null>(this.readUser());
 get token(){return localStorage.getItem('olp_token')}
 get isAuthenticated(){return !!this.token&&!!this.user()}
 get isAdmin(){return this.user()?.role==='ADMIN'}
 accept(r:AuthResponse){localStorage.setItem('olp_token',r.token);localStorage.setItem('olp_user',JSON.stringify(r.user));this.user.set(r.user)}
 logout(){this.api.logout().subscribe({complete:()=>this.clear(),error:()=>this.clear()})}
 private clear(){localStorage.removeItem('olp_token');localStorage.removeItem('olp_user');this.user.set(null);this.router.navigateByUrl('/login')}
 private readUser(){try{return JSON.parse(localStorage.getItem('olp_user')||'null') as User|null}catch{return null}}
}
