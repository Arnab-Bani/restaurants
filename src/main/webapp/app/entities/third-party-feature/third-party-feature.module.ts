import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FoodSharedModule } from '../../shared';

import {
    ThirdPartyFeatureService,
    ThirdPartyFeaturePopupService,
    ThirdPartyFeatureComponent,
    ThirdPartyFeatureDetailComponent,
    ThirdPartyFeatureDialogComponent,
    ThirdPartyFeaturePopupComponent,
    ThirdPartyFeatureDeletePopupComponent,
    ThirdPartyFeatureDeleteDialogComponent,
    thirdPartyFeatureRoute,
    thirdPartyFeaturePopupRoute,
    ThirdPartyFeatureResolvePagingParams,
} from './';

let ENTITY_STATES = [
    ...thirdPartyFeatureRoute,
    ...thirdPartyFeaturePopupRoute,
];

@NgModule({
    imports: [
        FoodSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ThirdPartyFeatureComponent,
        ThirdPartyFeatureDetailComponent,
        ThirdPartyFeatureDialogComponent,
        ThirdPartyFeatureDeleteDialogComponent,
        ThirdPartyFeaturePopupComponent,
        ThirdPartyFeatureDeletePopupComponent,
    ],
    entryComponents: [
        ThirdPartyFeatureComponent,
        ThirdPartyFeatureDialogComponent,
        ThirdPartyFeaturePopupComponent,
        ThirdPartyFeatureDeleteDialogComponent,
        ThirdPartyFeatureDeletePopupComponent,
    ],
    providers: [
        ThirdPartyFeatureService,
        ThirdPartyFeaturePopupService,
        ThirdPartyFeatureResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FoodThirdPartyFeatureModule {}
