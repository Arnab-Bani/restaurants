import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Principal, AccountService } from '../shared';
import { Client } from '../entities/client/client.model';
import { ClientService } from '../entities/client/client.service';

@Component({
    selector: 'jhi-settings',
    templateUrl: './location.component.html',
    styleUrls: ['location.component.css'],
})
export class LocationComponent implements OnInit {
    error: string;
    success: string;
    client: Client;
    private subscription: any;
    settingsAccount: any;
    languages: any[];
    zoom: number = 14;
    lat: number ;
    lng: number ;

    constructor(
        private account: AccountService,
        private clientService: ClientService,
        private principal: Principal,
        private route: ActivatedRoute
    ) {}

    ngOnInit () {
        this.subscription = this.route.params.subscribe(params => {
            this.load('1');
        });
    }

    load (id) {
        this.clientService.find(id).subscribe(client => {
            this.client = client;
  
            this.lat = client.latitude;
            this.lng = client.longitude;
        });
    }
}
