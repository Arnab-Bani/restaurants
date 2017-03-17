import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { DataUtils } from 'ng-jhipster';
import { ClientMenu } from './client-menu.model';
import { ClientMenuService } from './client-menu.service';

@Component({
    selector: 'jhi-client-menu-detail',
    templateUrl: './client-menu-detail.component.html'
})
export class ClientMenuDetailComponent implements OnInit, OnDestroy {

    clientMenu: ClientMenu;
    private subscription: any;

    constructor(
        private dataUtils: DataUtils,
        private clientMenuService: ClientMenuService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.clientMenuService.find(id).subscribe(clientMenu => {
            this.clientMenu = clientMenu;
        });
    }
    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
