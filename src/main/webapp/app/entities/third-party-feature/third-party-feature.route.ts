import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { ThirdPartyFeatureComponent } from './third-party-feature.component';
import { ThirdPartyFeatureDetailComponent } from './third-party-feature-detail.component';
import { ThirdPartyFeaturePopupComponent } from './third-party-feature-dialog.component';
import { ThirdPartyFeatureDeletePopupComponent } from './third-party-feature-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class ThirdPartyFeatureResolvePagingParams implements Resolve<any> {

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

export const thirdPartyFeatureRoute: Routes = [
  {
    path: 'third-party-feature',
    component: ThirdPartyFeatureComponent,
    resolve: {
      'pagingParams': ThirdPartyFeatureResolvePagingParams
    },
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'ThirdPartyFeatures'
    }
  }, {
    path: 'third-party-feature/:id',
    component: ThirdPartyFeatureDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'ThirdPartyFeatures'
    }
  }
];

export const thirdPartyFeaturePopupRoute: Routes = [
  {
    path: 'third-party-feature-new',
    component: ThirdPartyFeaturePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'ThirdPartyFeatures'
    },
    outlet: 'popup'
  },
  {
    path: 'third-party-feature/:id/edit',
    component: ThirdPartyFeaturePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'ThirdPartyFeatures'
    },
    outlet: 'popup'
  },
  {
    path: 'third-party-feature/:id/delete',
    component: ThirdPartyFeatureDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'ThirdPartyFeatures'
    },
    outlet: 'popup'
  }
];
