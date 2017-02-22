import { Route } from '@angular/router';

import { UserRouteAccessService } from '../shared';
import { LocationComponent } from './location.component';

export const locationRoute: Route = {
  path: 'location',
  component: LocationComponent,
  data: {
    authorities: ['ROLE_USER'],
    pageTitle: 'Location'
  },
  canActivate: [UserRouteAccessService]
};
