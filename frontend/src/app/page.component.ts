import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { debounceTime } from 'rxjs';
import { ApiService } from './api.service';
import { AuthService } from './auth.service';
import { Course, CoursePayload, Dashboard, Enrollment } from './models';
@Component({selector:'app-page',standalone:true,imports:[CommonModule,ReactiveFormsModule,RouterLink],templateUrl:'./page.component.html'})
export class PageComponent implements OnInit {
 kind='home';api=inject(ApiService);auth=inject(AuthService);fb=inject(FormBuilder);route=inject(ActivatedRoute);router=inject(Router);
 courses:Course[]=[]; myCourses:Enrollment[]=[]; course:Course|null=null; stats:Dashboard|null=null; students:any[]=[]; enrollments:Enrollment[]=[]; error='';success='';busy=false;editingId:number|null=null;showCourseForm=false;
 filters=this.fb.group({search:[''],category:[''],level:['']});
 authForm=this.fb.group({name:['',Validators.required],email:['',[Validators.required,Validators.email]],password:['',[Validators.required,Validators.minLength(8)]]});
 profileForm=this.fb.group({name:['',[Validators.required,Validators.maxLength(100)]],email:['',[Validators.required,Validators.email]]});
 courseForm=this.fb.group({title:['',[Validators.required,Validators.maxLength(180)]],instructor:['',Validators.required],category:['',Validators.required],level:['Beginner',Validators.required],duration:['',Validators.required],imageUrl:[''],description:['',Validators.required]});
 ngOnInit(){this.route.data.subscribe(d=>{this.kind=d['kind'];if(this.kind==='login')this.authForm.controls.name.clearValidators();else this.authForm.controls.name.setValidators(Validators.required);this.authForm.controls.name.updateValueAndValidity();this.load()});this.route.paramMap.subscribe(p=>{if(this.kind==='detail'&&p.get('id'))this.api.course(Number(p.get('id'))).subscribe({next:c=>this.course=c,error:e=>this.fail(e)})});this.filters.valueChanges.pipe(debounceTime(250)).subscribe(()=>this.loadCourses())}
 load(){this.error='';if(this.kind==='home'||this.kind==='courses'||this.kind==='admin-courses')this.loadCourses();if(this.kind==='my-courses')this.api.myCourses().subscribe({next:x=>this.myCourses=x,error:e=>this.fail(e)});if(this.kind==='profile')this.api.profile().subscribe({next:u=>this.profileForm.patchValue({name:u.name,email:u.email}),error:e=>this.fail(e)});if(this.kind==='dashboard')this.api.dashboard().subscribe({next:s=>this.stats=s,error:e=>this.fail(e)});if(this.kind==='students')this.api.students().subscribe({next:s=>this.students=s,error:e=>this.fail(e)});if(this.kind==='enrollments')this.api.enrollments().subscribe({next:e=>this.enrollments=e,error:e=>this.fail(e)});if(this.kind==='detail'){const id=this.route.snapshot.paramMap.get('id');if(id)this.api.course(Number(id)).subscribe({next:c=>this.course=c,error:e=>this.fail(e)})}}
 loadCourses(){const v=this.filters.getRawValue();this.api.courses({search:v.search||undefined,category:v.category||undefined,level:v.level||undefined}).subscribe({next:c=>this.courses=c,error:e=>this.fail(e)})}
 submitAuth(){if(this.authForm.invalid){this.authForm.markAllAsTouched();return}this.busy=true;const v=this.authForm.getRawValue();const req=this.kind==='register'?this.api.register({name:v.name!,email:v.email!,password:v.password!}):this.api.login({email:v.email!,password:v.password!});req.subscribe({next:r=>{this.auth.accept(r);this.busy=false;this.router.navigateByUrl(r.user.role==='ADMIN'?'/admin':'/')},error:e=>{this.busy=false;this.fail(e)}})}
 enroll(c:Course){if(!this.auth.isAuthenticated){this.router.navigate(['/login'],{queryParams:{returnUrl:`/courses/${c.id}`}});return}this.api.enroll(c.id).subscribe({next:()=>{this.success='You’re enrolled! Find it in My learning.';this.loadCourses()},error:e=>this.fail(e)})}
 saveProfile(){if(this.profileForm.invalid){this.profileForm.markAllAsTouched();return}this.api.updateProfile(this.profileForm.getRawValue() as {name:string;email:string}).subscribe({next:u=>{this.auth.user.set(u);localStorage.setItem('olp_user',JSON.stringify(u));this.success='Profile updated.'},error:e=>this.fail(e)})}
 newCourse(){this.editingId=null;this.showCourseForm=true;this.courseForm.reset({level:'Beginner',imageUrl:''})}
 cancelCourse(){this.showCourseForm=false;this.editingId=null;this.courseForm.reset({level:'Beginner',imageUrl:''})}
 editCourse(c:Course){this.editingId=c.id;this.showCourseForm=true;this.courseForm.patchValue(c)}
 saveCourse(){if(this.courseForm.invalid){this.courseForm.markAllAsTouched();return}const v=this.courseForm.getRawValue() as CoursePayload;const req=this.editingId?this.api.updateCourse(this.editingId,v):this.api.createCourse(v);req.subscribe({next:()=>{this.success=this.editingId?'Course updated.':'Course created.';this.editingId=null;this.showCourseForm=false;this.courseForm.reset({level:'Beginner',imageUrl:''});this.loadCourses()},error:e=>this.fail(e)})}
 deleteCourse(c:Course){if(!confirm(`Delete “${c.title}”? Courses with enrollments cannot be removed.`))return;this.api.deleteCourse(c.id).subscribe({next:()=>{this.success='Course deleted.';this.loadCourses()},error:e=>this.fail(e)})}
 fail(e:any){this.error=e?.error?.message||'The request could not be completed. Please try again.'}
 clearMessages(){this.error='';this.success=''}
 img(c:Course){return c.imageUrl||'https://images.unsplash.com/photo-1522202176988-66273c2fd55f?auto=format&fit=crop&w=900&q=80'}
}
