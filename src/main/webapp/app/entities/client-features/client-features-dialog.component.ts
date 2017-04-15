import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { ClientFeatures } from './client-features.model';
import { ClientFeaturesPopupService } from './client-features-popup.service';
import { ClientFeaturesService } from './client-features.service';
import { Client, ClientService } from '../client';
import { ThirdPartyFeature, ThirdPartyFeatureService } from '../third-party-feature';
@Component({
    selector: 'jhi-client-features-dialog',
    templateUrl: './client-features-dialog.component.html'
})
export class ClientFeaturesDialogComponent implements OnInit {

    clientFeatures: ClientFeatures;
    authorities: any[];
    isSaving: boolean;

    clients: Client[];

    thirdpartyfeatures: ThirdPartyFeature[];
    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private clientFeaturesService: ClientFeaturesService,
        private clientService: ClientService,
        private thirdPartyFeatureService: ThirdPartyFeatureService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.clientService.query().subscribe(
            (res: Response) => { this.clients = res.json(); }, (res: Response) => this.onError(res.json()));
        this.thirdPartyFeatureService.query().subscribe(
            (res: Response) => { this.thirdpartyfeatures = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    clear () {
        this.activeModal.dismiss('cancel');
    }

    save () {
        this.isSaving = true;
        if (this.clientFeatures.id !== undefined) {
            this.clientFeaturesService.update(this.clientFeatures)
                .subscribe((res: ClientFeatures) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.clientFeaturesService.create(this.clientFeatures)
                .subscribe((res: ClientFeatures) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess (result: ClientFeatures) {
        this.eventManager.broadcast({ name: 'clientFeaturesListModification', content: 'OK'});
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

    trackClientById(index: number, item: Client) {
        return item.id;
    }

    trackThirdPartyFeatureById(index: number, item: ThirdPartyFeature) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-client-features-popup',
    template: ''
})
export class ClientFeaturesPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private clientFeaturesPopupService: ClientFeaturesPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.clientFeaturesPopupService
                    .open(ClientFeaturesDialogComponent, params['id']);
            } else {
                this.modalRef = this.clientFeaturesPopupService
                    .open(ClientFeaturesDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
