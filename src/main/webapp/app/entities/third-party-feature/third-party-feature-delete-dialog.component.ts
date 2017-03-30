import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { ThirdPartyFeature } from './third-party-feature.model';
import { ThirdPartyFeaturePopupService } from './third-party-feature-popup.service';
import { ThirdPartyFeatureService } from './third-party-feature.service';

@Component({
    selector: 'jhi-third-party-feature-delete-dialog',
    templateUrl: './third-party-feature-delete-dialog.component.html'
})
export class ThirdPartyFeatureDeleteDialogComponent {

    thirdPartyFeature: ThirdPartyFeature;

    constructor(
        private thirdPartyFeatureService: ThirdPartyFeatureService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear () {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete (id: number) {
        this.thirdPartyFeatureService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'thirdPartyFeatureListModification',
                content: 'Deleted an thirdPartyFeature'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-third-party-feature-delete-popup',
    template: ''
})
export class ThirdPartyFeatureDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private thirdPartyFeaturePopupService: ThirdPartyFeaturePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.thirdPartyFeaturePopupService
                .open(ThirdPartyFeatureDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
