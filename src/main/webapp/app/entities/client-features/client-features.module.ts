import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FoodSharedModule } from '../../shared';

import {
    Client_featuresService,
    Client_featuresPopupService,
    Client_featuresComponent,
    Client_featuresDetailComponent,
    Client_featuresDialogComponent,
    Client_featuresPopupComponent,
    Client_featuresDeletePopupComponent,
    Client_featuresDeleteDialogComponent,
    client_featuresRoute,
    client_featuresPopupRoute,
} from './';

let ENTITY_STATES = [
    ...client_featuresRoute,
    ...client_featuresPopupRoute,
];

@NgModule({
    imports: [
        FoodSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        Client_featuresComponent,
        Client_featuresDetailComponent,
        Client_featuresDialogComponent,
        Client_featuresDeleteDialogComponent,
        Client_featuresPopupComponent,
        Client_featuresDeletePopupComponent,
    ],
    entryComponents: [
        Client_featuresComponent,
        Client_featuresDialogComponent,
        Client_featuresPopupComponent,
        Client_featuresDeleteDialogComponent,
        Client_featuresDeletePopupComponent,
    ],
    providers: [
        Client_featuresService,
        Client_featuresPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FoodClient_featuresModule {}
