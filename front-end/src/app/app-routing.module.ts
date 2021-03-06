import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { RegisterComponent } from './register/register.component';
import { LoginComponent } from './login/login.component';
import { HomeComponent } from './home/home.component';
import { UserComponent } from './user/user.component';
import { AdminComponent } from './admin/admin.component';
import {ShapePanelComponent} from "./admin/shape-panel/shape-panel.component";
import {SkillPanelComponent} from "./admin/skill-panel/skill-panel.component";
import {UserPanelComponent} from "./admin/user-panel/user-panel.component";
import {InactiveSiteComponent} from "./inactive-site/inactive-site.component";
import {AddEditShapeComponent} from "./admin/shape-panel/add-edit-shape/add-edit-shape.component";
import {AddEditSkillComponent} from "./admin/skill-panel/add-edit-skill/add-edit-skill.component";
import {ColormapPanelComponent} from "./admin/colormap-panel/colormap-panel.component";
import {AddEditColormapComponent} from "./admin/colormap-panel/add-edit-colormap/add-edit-colormap.component";
import {XpthresholdPanelComponent} from "./admin/xpthreshold-panel/xpthreshold-panel.component";
import {FightWindowComponent} from "./fight-window/fight-window.component";
import {AdminGuard} from "./guards/admin.guard";
import {MaintenanceGuard} from "./guards/maintenance.guard";
import {MaintenanceReverseGuard} from "./guards/maintenance-reverse.guard";
import {MaintenanceFightGuard} from "./guards/maintenance-fight.guard";

const routes: Routes = [
    {
        path: 'home',
        component: HomeComponent,
    },
    {
        path: 'user',
        component: UserComponent,
        canActivate:[MaintenanceFightGuard]
    },
    {
        path: 'user/:username',
        component: UserComponent,
        canActivate:[MaintenanceFightGuard]
    },
    {
        path: 'error',
        component: InactiveSiteComponent,
        canActivate:[MaintenanceReverseGuard]
    },
    {
        path: 'fight',
        component:FightWindowComponent,
        canActivate:[MaintenanceGuard]
    },
    {
        path: 'admin',
        component: AdminComponent,
        canActivate:[AdminGuard],
        children:[
          {
            path: '',
            redirectTo: 'shapes',
            pathMatch: 'full'
          },
          {
            path:'thresholds',
            component:XpthresholdPanelComponent
          },
          {
            path: 'shapes',
            component: ShapePanelComponent,
            pathMatch: 'full'
          },
          {
            path: 'shapes/:id',
            component: AddEditShapeComponent,
          },
          {
            path: 'shapes/:create',
            component: AddEditShapeComponent,
          },
          {
            path: 'colors',
            component: ColormapPanelComponent,
            pathMatch: 'full'
          },
          {
            path: 'colors/:id',
            component: AddEditColormapComponent,
          },
          {
            path: 'colors/:create',
            component: AddEditColormapComponent,
          },
          {
            path: 'skills',
            component: SkillPanelComponent,
            pathMatch: 'full'
          },
          {
            path: 'skills/:id',
            component: AddEditSkillComponent
          },
          {
            path: 'skills/create',
            component: AddEditSkillComponent
          },
          {
            path: 'users',
            component: UserPanelComponent,
            pathMatch: 'full'
          },
        ]
    },
    {
        path: 'auth/login',
        component: LoginComponent
    },
    {
        path: 'signup',
        component: RegisterComponent,
        canActivate:[MaintenanceGuard]
    },
    {
        path: '',
        redirectTo: 'home',
        pathMatch: 'full'
    }
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class AppRoutingModule { }
