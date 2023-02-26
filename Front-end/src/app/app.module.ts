import { APP_INITIALIZER, NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { SharedModule } from './shared/shared.module';
import { HomeModule } from './home/home.module';
import { LoginModule } from './login/login.module';
import { HttpClient, HTTP_INTERCEPTORS } from '@angular/common/http';
import { GlobalErrorsInterceptor } from './interceptors/global-errors.interceptor';
import { DashboardModule } from './dashboard/dashboard.module';
import { AdminModule } from './admin/admin.module';
import { JwtInterceptor } from './interceptors/jwt.interceptor';
import { AdminGuard } from './guards/admin.guard';
import { EnvironmentFactoryService } from './config/environment-factory.service';

export function initApp(environmentFactory: EnvironmentFactoryService) {
  return () => {
    console.log(
      `Environment: ${JSON.stringify(environmentFactory.getEnvironment())}`
    );
  };
}

@NgModule({
  declarations: [AppComponent],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    SharedModule,
    HomeModule,
    LoginModule,
    DashboardModule,
    AdminModule,
  ],
  providers: [
    AdminGuard,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: GlobalErrorsInterceptor,
      multi: true,
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: JwtInterceptor,
      multi: true,
    },
    EnvironmentFactoryService,
    {
      provide: APP_INITIALIZER,
      useFactory: initApp,
      deps: [EnvironmentFactoryService],
      multi: true,
    },
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
