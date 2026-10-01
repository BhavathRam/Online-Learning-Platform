import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthResponse, Course, CoursePayload, Dashboard, Enrollment, User } from './models';
@Injectable({providedIn:'root'}) export class ApiService {
 private http=inject(HttpClient); private base='http://localhost:8080/api';
 register(value:{name:string;email:string;password:string}):Observable<AuthResponse>{return this.http.post<AuthResponse>(this.base+'/auth/register',value)}
 login(value:{email:string;password:string}):Observable<AuthResponse>{return this.http.post<AuthResponse>(this.base+'/auth/login',value)}
 logout(){return this.http.post<void>(this.base+'/auth/logout',{})}
 courses(filters:{search?:string;category?:string;level?:string}={}){let params=new HttpParams();Object.entries(filters).forEach(([k,v])=>{if(v)params=params.set(k,v)});return this.http.get<Course[]>(this.base+'/courses',{params})}
 course(id:number){return this.http.get<Course>(`${this.base}/courses/${id}`)}
 createCourse(value:CoursePayload){return this.http.post<Course>(this.base+'/courses',value)}
 updateCourse(id:number,value:CoursePayload){return this.http.put<Course>(`${this.base}/courses/${id}`,value)}
 deleteCourse(id:number){return this.http.delete<void>(`${this.base}/courses/${id}`)}
 enroll(id:number){return this.http.post<Enrollment>(`${this.base}/enrollments/${id}`,{})}
 myCourses(){return this.http.get<Enrollment[]>(this.base+'/enrollments/me')}
 enrollments(){return this.http.get<Enrollment[]>(this.base+'/enrollments')}
 profile(){return this.http.get<User>(this.base+'/users/me')}
 updateProfile(value:{name:string;email:string}){return this.http.put<User>(this.base+'/users/me',value)}
 students(){return this.http.get<any[]>(this.base+'/users')}
 dashboard(){return this.http.get<Dashboard>(this.base+'/admin/dashboard')}
}
