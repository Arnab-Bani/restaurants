import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FoodSharedModule } from '../../shared';

import {
    ClientMenuService,
    ClientMenuPopupService,
    ClientMenuComponent,
    ClientMenuDetailComponent,
    ClientMenuDialogComponent,
    ClientMenuPopupComponent,
    ClientMenuDeletePopupComponent,
    ClientMenuDeleteDialogComponent,
    clientMenuRoute,
    clientMenuPopupRoute,
    ClientMenuResolvePagingParams,
} from './';

let ENTITY_STATES = [
    ...clientMenuRoute,
    ...clientMenuPopupRoute,
];

@NgModule({
    imports: [
        FoodSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ClientMenuComponent,
        ClientMenuDetailComponent,
        ClientMenuDialogComponent,
        ClientMenuDeleteDialogComponent,
        ClientMenuPopupComponent,
        ClientMenuDeletePopupComponent,
    ],
    entryComponents: [
        ClientMenuComponent,
        ClientMenuDialogComponent,
        ClientMenuPopupComponent,
        ClientMenuDeleteDialogComponent,
        ClientMenuDeletePopupComponent,
    ],
    providers: [
        ClientMenuService,
        ClientMenuPopupService,
        ClientMenuResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FoodClientMenuModule {}
