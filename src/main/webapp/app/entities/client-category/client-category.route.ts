import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { ClientCategoryComponent } from './client-category.component';
import { ClientCategoryDetailComponent } from './client-category-detail.component';
import { ClientCategoryPopupComponent } from './client-category-dialog.component';
import { ClientCategoryDeletePopupComponent } from './client-category-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class ClientCategoryResolvePagingParams implements Resolve<any> {

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

export const clientCategoryRoute: Routes = [
  {
    path: 'client-category',
    component: ClientCategoryComponent,
    resolve: {
      'pagingParams': ClientCategoryResolvePagingParams
    },
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'ClientCategories'
    }
  }, {
    path: 'client-category/:id',
    component: ClientCategoryDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'ClientCategories'
    }
  }
];

export const clientCategoryPopupRoute: Routes = [
  {
    path: 'client-category-new',
    component: ClientCategoryPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'ClientCategories'
    },
    outlet: 'popup'
  },
  {
    path: 'client-category/:id/edit',
    component: ClientCategoryPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'ClientCategories'
    },
    outlet: 'popup'
  },
  {
    path: 'client-category/:id/delete',
    component: ClientCategoryDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'ClientCategories'
    },
    outlet: 'popup'
  }
];
