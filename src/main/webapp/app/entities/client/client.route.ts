import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { ClientComponent } from './client.component';
import { ClientDetailComponent } from './client-detail.component';
import { ClientPopupComponent } from './client-dialog.component';
import { ClientDeletePopupComponent } from './client-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class ClientResolvePagingParams implements Resolve<any> {

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

export const clientRoute: Routes = [
  {
    path: 'client',
    component: ClientComponent,
    resolve: {
      'pagingParams': ClientResolvePagingParams
    },
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Clients'
    }
  }, {
    path: 'client/:id',
    component: ClientDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Clients'
    }
  }
];

export const clientPopupRoute: Routes = [
  {
    path: 'client-new',
    component: ClientPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Clients'
    },
    outlet: 'popup'
  },
  {
    path: 'client/:id/edit',
    component: ClientPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Clients'
    },
    outlet: 'popup'
  },
  {
    path: 'client/:id/delete',
    component: ClientDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Clients'
    },
    outlet: 'popup'
  }
];
