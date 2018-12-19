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

const routes: Routes = [
    {
        path: 'home',
        component: HomeComponent
    },
    {
        path: 'user',
        component: UserComponent
    },
    {
        path: 'error',
        component: InactiveSiteComponent
    },
    {
        path: 'admin',
        component: AdminComponent,
        children:[
          {
            path: '',
            redirectTo: 'shapes',
            pathMatch: 'full'
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
            path: 'users',
            component: UserPanelComponent,
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
        ]
    },
    {
        path: 'auth/login',
        component: LoginComponent
    },
    {
        path: 'signup',
        component: RegisterComponent
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
