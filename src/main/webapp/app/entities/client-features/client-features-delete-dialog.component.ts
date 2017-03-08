import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { ClientFeatures } from './client-features.model';
import { ClientFeaturesPopupService } from './client-features-popup.service';
import { ClientFeaturesService } from './client-features.service';

@Component({
    selector: 'jhi-client-features-delete-dialog',
    templateUrl: './client-features-delete-dialog.component.html'
})
export class ClientFeaturesDeleteDialogComponent {

    clientFeatures: ClientFeatures;

    constructor(
        private clientFeaturesService: ClientFeaturesService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear () {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete (id: number) {
        this.clientFeaturesService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'clientFeaturesListModification',
                content: 'Deleted an clientFeatures'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-client-features-delete-popup',
    template: ''
})
export class ClientFeaturesDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private clientFeaturesPopupService: ClientFeaturesPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.clientFeaturesPopupService
                .open(ClientFeaturesDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
