import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { ClientFeaturesComponent } from './client-features.component';
import { ClientFeaturesDetailComponent } from './client-features-detail.component';
import { ClientFeaturesPopupComponent } from './client-features-dialog.component';
import { ClientFeaturesDeletePopupComponent } from './client-features-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class ClientFeaturesResolvePagingParams implements Resolve<any> {

  constructor(private paginationUtil: PaginationUtil) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
      let page = route.queryParams['page'] ? route.queryParams['page'] : '1';
      let sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
      return {
          page: this.paginationUtil.parsePage(page),
          predicate: this.paginationUtil.parsePredicate(sort),
          ascending: this.paginationUtil.parseAscending(sort)
    };
  }
}

export const clientFeaturesRoute: Routes = [
  {
    path: 'client-features',
    component: ClientFeaturesComponent,
    resolve: {
      'pagingParams': ClientFeaturesResolvePagingParams
    },
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'ClientFeatures'
    }
  }, {
    path: 'client-features/:id',
    component: ClientFeaturesDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'ClientFeatures'
    }
  }
];

export const clientFeaturesPopupRoute: Routes = [
  {
    path: 'client-features-new',
    component: ClientFeaturesPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'ClientFeatures'
    },
    outlet: 'popup'
  },
  {
    path: 'client-features/:id/edit',
    component: ClientFeaturesPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'ClientFeatures'
    },
    outlet: 'popup'
  },
  {
    path: 'client-features/:id/delete',
    component: ClientFeaturesDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'ClientFeatures'
    },
    outlet: 'popup'
  }
];
