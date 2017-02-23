import { Component, OnInit } from '@angular/core';

import { Principal, AccountService } from '../shared';

@Component({
    selector: 'jhi-settings',
    templateUrl: './menu.component.html'
})
export class MenuComponent implements OnInit {
    error: string;
    success: string;
    settingsAccount: any;
    languages: any[];

    ngOnInit () {

    }
}
