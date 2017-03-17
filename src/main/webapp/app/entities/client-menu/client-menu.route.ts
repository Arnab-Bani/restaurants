import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { ClientMenuComponent } from './client-menu.component';
import { ClientMenuDetailComponent } from './client-menu-detail.component';
import { ClientMenuPopupComponent } from './client-menu-dialog.component';
import { ClientMenuDeletePopupComponent } from './client-menu-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class ClientMenuResolvePagingParams implements Resolve<any> {

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

export const clientMenuRoute: Routes = [
  {
    path: 'client-menu',
    component: ClientMenuComponent,
    resolve: {
      'pagingParams': ClientMenuResolvePagingParams
    },
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'ClientMenus'
    }
  }, {
    path: 'client-menu/:id',
    component: ClientMenuDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'ClientMenus'
    }
  }
];

export const clientMenuPopupRoute: Routes = [
  {
    path: 'client-menu-new',
    component: ClientMenuPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'ClientMenus'
    },
    outlet: 'popup'
  },
  {
    path: 'client-menu/:id/edit',
    component: ClientMenuPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'ClientMenus'
    },
    outlet: 'popup'
  },
  {
    path: 'client-menu/:id/delete',
    component: ClientMenuDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'ClientMenus'
    },
    outlet: 'popup'
  }
];
