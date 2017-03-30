import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { FoodClientModule } from './client/client.module';
import { FoodClientFeaturesModule } from './client-features/client-features.module';
import { FoodClientMenuModule } from './client-menu/client-menu.module';
import { FoodClientCategoryModule } from './client-category/client-category.module';
import { FoodThirdPartyFeatureModule } from './third-party-feature/third-party-feature.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        FoodClientModule,
        FoodClientCategoryModule,
        FoodClientFeaturesModule,
        FoodClientMenuModule,
        FoodClientCategoryModule,
        FoodThirdPartyFeatureModule
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FoodEntityModule {}
