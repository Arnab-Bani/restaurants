import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FoodSharedModule } from '../../shared';

import {
    ClientCategoryService,
    ClientCategoryPopupService,
    ClientCategoryComponent,
    ClientCategoryDetailComponent,
    ClientCategoryDialogComponent,
    ClientCategoryPopupComponent,
    ClientCategoryDeletePopupComponent,
    ClientCategoryDeleteDialogComponent,
    clientCategoryRoute,
    clientCategoryPopupRoute,
    ClientCategoryResolvePagingParams,
} from './';

let ENTITY_STATES = [
    ...clientCategoryRoute,
    ...clientCategoryPopupRoute,
];

@NgModule({
    imports: [
        FoodSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ClientCategoryComponent,
        ClientCategoryDetailComponent,
        ClientCategoryDialogComponent,
        ClientCategoryDeleteDialogComponent,
        ClientCategoryPopupComponent,
        ClientCategoryDeletePopupComponent,
    ],
    entryComponents: [
        ClientCategoryComponent,
        ClientCategoryDialogComponent,
        ClientCategoryPopupComponent,
        ClientCategoryDeleteDialogComponent,
        ClientCategoryDeletePopupComponent,
    ],
    providers: [
        ClientCategoryService,
        ClientCategoryPopupService,
        ClientCategoryResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FoodClientCategoryModule {}
