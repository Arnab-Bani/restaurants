import { NgModule } from '@angular/core';
import { RouterModule, Routes, Resolve } from '@angular/router';

import { navbarRoute } from '../app.route';
import { menuRoute } from '../menu/menu.route';
import { locationRoute } from '../location/location.route';
import { errorRoute } from './';

let LAYOUT_ROUTES = [
    navbarRoute,menuRoute,locationRoute,
    ...errorRoute
];

@NgModule({
  imports: [
    RouterModule.forRoot(LAYOUT_ROUTES, { useHash: true })
  ],
  exports: [
    RouterModule
  ]
})
export class LayoutRoutingModule {}
