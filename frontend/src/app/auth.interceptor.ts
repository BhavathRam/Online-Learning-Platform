import { HttpInterceptorFn } from '@angular/common/http';
export const jwtInterceptor:HttpInterceptorFn=(req,next)=>{const token=localStorage.getItem('olp_token');return next(token?req.clone({setHeaders:{Authorization:`Bearer ${token}`}}):req)};
