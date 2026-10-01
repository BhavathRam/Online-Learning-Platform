import { Routes } from '@angular/router';
import { PageComponent } from './page.component';
import { adminGuard, authGuard } from './guards';
export const routes:Routes=[
 {path:'',component:PageComponent,data:{kind:'home'}},
 {path:'login',component:PageComponent,data:{kind:'login'}},{path:'register',component:PageComponent,data:{kind:'register'}},
 {path:'courses',component:PageComponent,data:{kind:'courses'}},{path:'courses/:id',component:PageComponent,data:{kind:'detail'}},
 {path:'my-courses',component:PageComponent,data:{kind:'my-courses'},canActivate:[authGuard]},
 {path:'profile',component:PageComponent,data:{kind:'profile'},canActivate:[authGuard]},
 {path:'admin',component:PageComponent,data:{kind:'dashboard'},canActivate:[adminGuard]},
 {path:'admin/courses',component:PageComponent,data:{kind:'admin-courses'},canActivate:[adminGuard]},
 {path:'admin/students',component:PageComponent,data:{kind:'students'},canActivate:[adminGuard]},
 {path:'admin/enrollments',component:PageComponent,data:{kind:'enrollments'},canActivate:[adminGuard]},
 {path:'**',redirectTo:''}
];
