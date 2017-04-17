import { Component, OnInit, Inject } from '@angular/core';
import { Router, ActivatedRouteSnapshot, NavigationEnd, RoutesRecognized } from '@angular/router';

import { Title } from '@angular/platform-browser';

import { StateStorageService } from '../../shared';
import { Client } from '../../entities/client/client.model';
import { ClientService } from '../../entities/client/client.service';
import { RiyazConstants } from '../../entities/client/riyaz.constants';

@Component({
    selector: 'jhi-main',
    templateUrl: './main.component.html'
})
export class JhiMainComponent implements OnInit {

    constructor(
        private riyazConstants: RiyazConstants,
        private clientService: ClientService,
        private titleService: Title,
        private router: Router,
        private $storageService: StateStorageService,
    ) {}

    private getPageTitle(routeSnapshot: ActivatedRouteSnapshot) {
        let title: string = (routeSnapshot.data && routeSnapshot.data['pageTitle']) ? routeSnapshot.data['pageTitle'] : 'foodApp';
        if (routeSnapshot.firstChild) {
            title = this.getPageTitle(routeSnapshot.firstChild) || title;
        }
        return title;
    }

    ngOnInit() {
        console.log('this should be first element : '+window.location.hostname);
        this.clientService.findByWebsite(window.location.hostname).subscribe(client => {
            this.riyazConstants.clientId = client.id;
            console.log('Logged in clientId : '+this.riyazConstants.clientId);
        });
        this.router.events.subscribe((event) => {
            if (event instanceof NavigationEnd) {
                 this.titleService.setTitle(this.getPageTitle(this.router.routerState.snapshot.root));
            }
            if (event instanceof RoutesRecognized) {
                let params = {};
                let destinationData = {};
                let destinationName = '';
                let destinationEvent = event.state.root.firstChild.children[0];
                if (destinationEvent !== undefined) {
                    params = destinationEvent.params;
                    destinationData = destinationEvent.data;
                    destinationName = destinationEvent.url[0].path;
                }
                let from = {name: this.router.url.slice(1)};
                let destination = {name: destinationName, data: destinationData};
                this.$storageService.storeDestinationState(destination, params, from);
            }
        });
    }
}
