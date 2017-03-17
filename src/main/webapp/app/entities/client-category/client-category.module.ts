import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FoodSharedModule } from '../../shared';

import {
    Client_categoryService,
    Client_categoryPopupService,
    Client_categoryComponent,
    Client_categoryDetailComponent,
    Client_categoryDialogComponent,
    Client_categoryPopupComponent,
    Client_categoryDeletePopupComponent,
    Client_categoryDeleteDialogComponent,
    client_categoryRoute,
    client_categoryPopupRoute,
    Client_categoryResolvePagingParams,
} from './';

let ENTITY_STATES = [
    ...client_categoryRoute,
    ...client_categoryPopupRoute,
];

@NgModule({
    imports: [
        FoodSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        Client_categoryComponent,
        Client_categoryDetailComponent,
        Client_categoryDialogComponent,
        Client_categoryDeleteDialogComponent,
        Client_categoryPopupComponent,
        Client_categoryDeletePopupComponent,
    ],
    entryComponents: [
        Client_categoryComponent,
        Client_categoryDialogComponent,
        Client_categoryPopupComponent,
        Client_categoryDeleteDialogComponent,
        Client_categoryDeletePopupComponent,
    ],
    providers: [
        Client_categoryService,
        Client_categoryPopupService,
        Client_categoryResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FoodClientCategoryModule {}
