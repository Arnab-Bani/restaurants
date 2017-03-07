import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { Client_featuresComponent } from './client-features.component';
import { Client_featuresDetailComponent } from './client-features-detail.component';
import { Client_featuresPopupComponent } from './client-features-dialog.component';
import { Client_featuresDeletePopupComponent } from './client-features-delete-dialog.component';

import { Principal } from '../../shared';


export const client_featuresRoute: Routes = [
  {
    path: 'client-features',
    component: Client_featuresComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Client_features'
    }
  }, {
    path: 'client-features/:id',
    component: Client_featuresDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Client_features'
    }
  }
];

export const client_featuresPopupRoute: Routes = [
  {
    path: 'client-features-new',
    component: Client_featuresPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Client_features'
    },
    outlet: 'popup'
  },
  {
    path: 'client-features/:id/edit',
    component: Client_featuresPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Client_features'
    },
    outlet: 'popup'
  },
  {
    path: 'client-features/:id/delete',
    component: Client_featuresDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Client_features'
    },
    outlet: 'popup'
  }
];
