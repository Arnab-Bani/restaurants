import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { ThirdPartyFeature } from './third-party-feature.model';
import { ThirdPartyFeatureService } from './third-party-feature.service';
@Injectable()
export class ThirdPartyFeaturePopupService {
    private isOpen = false;
    constructor (
        private modalService: NgbModal,
        private router: Router,
        private thirdPartyFeatureService: ThirdPartyFeatureService
    ) {}

    open (component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.thirdPartyFeatureService.find(id).subscribe(thirdPartyFeature => {
                this.thirdPartyFeatureModalRef(component, thirdPartyFeature);
            });
        } else {
            return this.thirdPartyFeatureModalRef(component, new ThirdPartyFeature());
        }
    }

    thirdPartyFeatureModalRef(component: Component, thirdPartyFeature: ThirdPartyFeature): NgbModalRef {
        let modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.thirdPartyFeature = thirdPartyFeature;
        modalRef.result.then(result => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        });
        return modalRef;
    }
}
