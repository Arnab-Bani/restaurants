import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { ClientMenu } from './client-menu.model';
import { ClientMenuPopupService } from './client-menu-popup.service';
import { ClientMenuService } from './client-menu.service';

@Component({
    selector: 'jhi-client-menu-delete-dialog',
    templateUrl: './client-menu-delete-dialog.component.html'
})
export class ClientMenuDeleteDialogComponent {

    clientMenu: ClientMenu;

    constructor(
        private clientMenuService: ClientMenuService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear () {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete (id: number) {
        this.clientMenuService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'clientMenuListModification',
                content: 'Deleted an clientMenu'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-client-menu-delete-popup',
    template: ''
})
export class ClientMenuDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private clientMenuPopupService: ClientMenuPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.clientMenuPopupService
                .open(ClientMenuDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
