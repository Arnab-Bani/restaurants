import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FoodSharedModule } from '../../shared';

import {
    ClientFeaturesService,
    ClientFeaturesPopupService,
    ClientFeaturesComponent,
    ClientFeaturesDetailComponent,
    ClientFeaturesDialogComponent,
    ClientFeaturesPopupComponent,
    ClientFeaturesDeletePopupComponent,
    ClientFeaturesDeleteDialogComponent,
    clientFeaturesRoute,
    clientFeaturesPopupRoute,
} from './';

let ENTITY_STATES = [
    ...clientFeaturesRoute,
    ...clientFeaturesPopupRoute,
];

@NgModule({
    imports: [
        FoodSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ClientFeaturesComponent,
        ClientFeaturesDetailComponent,
        ClientFeaturesDialogComponent,
        ClientFeaturesDeleteDialogComponent,
        ClientFeaturesPopupComponent,
        ClientFeaturesDeletePopupComponent,
    ],
    entryComponents: [
        ClientFeaturesComponent,
        ClientFeaturesDialogComponent,
        ClientFeaturesPopupComponent,
        ClientFeaturesDeleteDialogComponent,
        ClientFeaturesDeletePopupComponent,
    ],
    providers: [
        ClientFeaturesService,
        ClientFeaturesPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FoodClientFeaturesModule {}
