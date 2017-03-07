import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { Client_features } from './client-features.model';
import { Client_featuresPopupService } from './client-features-popup.service';
import { Client_featuresService } from './client-features.service';

@Component({
    selector: 'jhi-client-features-delete-dialog',
    templateUrl: './client-features-delete-dialog.component.html'
})
export class Client_featuresDeleteDialogComponent {

    client_features: Client_features;

    constructor(
        private client_featuresService: Client_featuresService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear () {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete (id: number) {
        this.client_featuresService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'client_featuresListModification',
                content: 'Deleted an client_features'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-client-features-delete-popup',
    template: ''
})
export class Client_featuresDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private client_featuresPopupService: Client_featuresPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.client_featuresPopupService
                .open(Client_featuresDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
