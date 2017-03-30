import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { ThirdPartyFeature } from './third-party-feature.model';
import { ThirdPartyFeaturePopupService } from './third-party-feature-popup.service';
import { ThirdPartyFeatureService } from './third-party-feature.service';
@Component({
    selector: 'jhi-third-party-feature-dialog',
    templateUrl: './third-party-feature-dialog.component.html'
})
export class ThirdPartyFeatureDialogComponent implements OnInit {

    thirdPartyFeature: ThirdPartyFeature;
    authorities: any[];
    isSaving: boolean;
    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private thirdPartyFeatureService: ThirdPartyFeatureService,
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
        if (this.thirdPartyFeature.id !== undefined) {
            this.thirdPartyFeatureService.update(this.thirdPartyFeature)
                .subscribe((res: ThirdPartyFeature) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.thirdPartyFeatureService.create(this.thirdPartyFeature)
                .subscribe((res: ThirdPartyFeature) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess (result: ThirdPartyFeature) {
        this.eventManager.broadcast({ name: 'thirdPartyFeatureListModification', content: 'OK'});
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
    selector: 'jhi-third-party-feature-popup',
    template: ''
})
export class ThirdPartyFeaturePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private thirdPartyFeaturePopupService: ThirdPartyFeaturePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.thirdPartyFeaturePopupService
                    .open(ThirdPartyFeatureDialogComponent, params['id']);
            } else {
                this.modalRef = this.thirdPartyFeaturePopupService
                    .open(ThirdPartyFeatureDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
