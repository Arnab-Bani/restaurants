import { Injectable } from '@angular/core';
import { Route } from '@angular/router';
import { PaginationUtil } from 'ng-jhipster';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../shared';
import { MenuComponent } from './menu.component';
import { ClientMenuResolvePagingParams } from '../entities/client-menu';

export const menuRoute: Route = {
    path: 'menu',
    component: MenuComponent,
    resolve: {
      'pagingParams': ClientMenuResolvePagingParams
    },
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Menu'
    },
    canActivate: [UserRouteAccessService]
};
