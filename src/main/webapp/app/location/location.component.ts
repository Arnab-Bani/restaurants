import { Component, OnInit } from '@angular/core';

import { Principal, AccountService } from '../shared';

@Component({
    selector: 'jhi-settings',
    templateUrl: './location.component.html',
    styleUrls: ['location.component.css'],
})
export class LocationComponent implements OnInit {
    error: string;
    success: string;
    settingsAccount: any;
    languages: any[];
    lat: number = 40.380804;
    lng: number = -74.508550;
    zoom: number = 14;

    constructor(
        private account: AccountService,
        private principal: Principal
    ) {
        }

    ngOnInit () {

    }
}