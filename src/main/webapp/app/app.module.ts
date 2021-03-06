import './vendor.ts';

import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { BrowserModule } from '@angular/platform-browser';
import { Ng2Webstorage } from 'ng2-webstorage';

import { FoodSharedModule, UserRouteAccessService } from './shared';
import { FoodHomeModule } from './home/home.module';
import { FoodAdminModule } from './admin/admin.module';
import { FoodAccountModule } from './account/account.module';
import { FoodEntityModule } from './entities/entity.module';

import { LayoutRoutingModule } from './layouts';
import { customHttpProvider } from './blocks/interceptor/http.provider';
import { PaginationConfig } from './blocks/config/uib-pagination.config';
import { MenuComponent} from './menu/menu.component';
import { LocationComponent} from './location/location.component';
import { AgmCoreModule } from 'angular2-google-maps/core';

import {
    JhiMainComponent,
    NavbarComponent,
    FooterComponent,
    ProfileService,
    PageRibbonComponent,
    ErrorComponent
} from './layouts';

@NgModule({
    imports: [
        BrowserModule,
        LayoutRoutingModule,
        Ng2Webstorage.forRoot({ prefix: 'jhi', separator: '-'}),
        FoodSharedModule,
        FoodHomeModule,
        FoodAdminModule,
        FoodAccountModule,
        FoodEntityModule,
        AgmCoreModule.forRoot({
            apiKey: 'AIzaSyDKzqm80CE0RTvIOkEM8YDyfx3mFTKtdoQ'
        })
    ],
    declarations: [
        JhiMainComponent,
        NavbarComponent,
        ErrorComponent,
        PageRibbonComponent,
        FooterComponent,
        MenuComponent,
        LocationComponent
    ],
    providers: [
        ProfileService,
        { provide: Window, useValue: window },
        { provide: Document, useValue: document },
        customHttpProvider(),
        PaginationConfig,
        UserRouteAccessService
    ],
    bootstrap: [ JhiMainComponent ]
})
export class FoodAppModule {}
