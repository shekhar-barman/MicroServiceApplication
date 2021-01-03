import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MicroAppsSharedModule } from 'app/shared/shared.module';
import { HOME_ROUTE } from './home.route';
import { HomeComponent } from './home.component';

@NgModule({
  imports: [MicroAppsSharedModule, RouterModule.forChild([HOME_ROUTE])],
  declarations: [HomeComponent],
})
export class MicroAppsHomeModule {}
