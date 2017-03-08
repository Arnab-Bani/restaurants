import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { ClientFeatures } from './client-features.model';
import { ClientFeaturesService } from './client-features.service';
@Injectable()
export class ClientFeaturesPopupService {
    private isOpen = false;
    constructor (
        private modalService: NgbModal,
        private router: Router,
        private clientFeaturesService: ClientFeaturesService
    ) {}

    open (component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.clientFeaturesService.find(id).subscribe(clientFeatures => {
                this.clientFeaturesModalRef(component, clientFeatures);
            });
        } else {
            return this.clientFeaturesModalRef(component, new ClientFeatures());
        }
    }

    clientFeaturesModalRef(component: Component, clientFeatures: ClientFeatures): NgbModalRef {
        let modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.clientFeatures = clientFeatures;
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
