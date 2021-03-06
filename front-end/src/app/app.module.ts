import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppRoutingModule } from './app-routing.module';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';

import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { UserComponent } from './user/user.component';
import { RegisterComponent } from './register/register.component';
import { HomeComponent } from './home/home.component';
import { AdminComponent } from './admin/admin.component';

import { httpInterceptorProviders } from './auth/auth-interceptor';
import { SplashPageComponent } from './home/splash-page/splash-page.component';
import { MainMenuComponent } from './home/main-menu/main-menu.component';
import { SideBarComponent } from './admin/side-bar/side-bar.component';
import { SkillPanelComponent } from './admin/skill-panel/skill-panel.component';
import { ShapePanelComponent } from './admin/shape-panel/shape-panel.component';
import { UserPanelComponent } from './admin/user-panel/user-panel.component';
import {InactiveSiteComponent} from "./inactive-site/inactive-site.component";
import { AddEditSkillComponent } from './admin/skill-panel/add-edit-skill/add-edit-skill.component';
import { AddEditShapeComponent } from './admin/shape-panel/add-edit-shape/add-edit-shape.component';
import {MatTabsModule} from '@angular/material/tabs';
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {
  MatProgressBarModule,
  MatSelectModule,
  MatSlideToggleModule,
  MatTableModule,
  MatTooltipModule
} from "@angular/material";
import { ColormapPanelComponent } from './admin/colormap-panel/colormap-panel.component';
import { AddEditColormapComponent } from './admin/colormap-panel/add-edit-colormap/add-edit-colormap.component';
import { XpthresholdPanelComponent } from './admin/xpthreshold-panel/xpthreshold-panel.component';
import { FightWindowComponent } from './fight-window/fight-window.component';
import { FighterDetailsComponent } from './user/fighter-details/fighter-details.component';
@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    UserComponent,
    RegisterComponent,
    HomeComponent,
    AdminComponent,
    SplashPageComponent,
    MainMenuComponent,
    SideBarComponent,
    SkillPanelComponent,
    ShapePanelComponent,
    UserPanelComponent,
    InactiveSiteComponent,
    AddEditSkillComponent,
    AddEditShapeComponent,
    ColormapPanelComponent,
    AddEditColormapComponent,
    XpthresholdPanelComponent,
    FightWindowComponent,
    FighterDetailsComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    NgbModule,
    MatTabsModule,
    BrowserAnimationsModule,
    MatTableModule,
    MatSelectModule,
    MatSlideToggleModule,
    MatProgressBarModule,
    MatTooltipModule
  ],
  providers: [httpInterceptorProviders],
  bootstrap: [AppComponent]
})
export class AppModule { }
