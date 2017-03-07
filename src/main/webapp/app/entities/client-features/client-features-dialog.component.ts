import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { Client_features } from './client-features.model';
import { Client_featuresPopupService } from './client-features-popup.service';
import { Client_featuresService } from './client-features.service';
@Component({
    selector: 'jhi-client-features-dialog',
    templateUrl: './client-features-dialog.component.html'
})
export class Client_featuresDialogComponent implements OnInit {

    client_features: Client_features;
    authorities: any[];
    isSaving: boolean;
    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private client_featuresService: Client_featuresService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
    }
    clear () {
        this.activeModal.dismiss('cancel');
    }

    save () {
        this.isSaving = true;
        if (this.client_features.id !== undefined) {
            this.client_featuresService.update(this.client_features)
                .subscribe((res: Client_features) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.client_featuresService.create(this.client_features)
                .subscribe((res: Client_features) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess (result: Client_features) {
        this.eventManager.broadcast({ name: 'client_featuresListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError (error) {
        this.isSaving = false;
        this.onError(error);
    }

    private onError (error) {
        this.alertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-client-features-popup',
    template: ''
})
export class Client_featuresPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private client_featuresPopupService: Client_featuresPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.client_featuresPopupService
                    .open(Client_featuresDialogComponent, params['id']);
            } else {
                this.modalRef = this.client_featuresPopupService
                    .open(Client_featuresDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
