import { Route } from '@angular/router';

import { UserRouteAccessService } from '../shared';
import { MenuComponent } from './menu.component';

export const menuRoute: Route = {
  path: 'menu',
  component: MenuComponent,
  data: {
    authorities: ['ROLE_USER'],
    pageTitle: 'Menu'
  },
  canActivate: [UserRouteAccessService]
};
