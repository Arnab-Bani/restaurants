import { Component, OnInit } from '@angular/core';

import { Principal, AccountService } from '../shared';

@Component({
    selector: 'jhi-settings',
    templateUrl: './location.component.html'
})
export class LocationComponent implements OnInit {
    error: string;
    success: string;
    settingsAccount: any;
    languages: any[];

    constructor(
        private account: AccountService,
        private principal: Principal
    ) {
        }

    ngOnInit () {

    }


}
