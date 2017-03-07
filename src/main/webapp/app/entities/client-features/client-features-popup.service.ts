import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Client_features } from './client-features.model';
import { Client_featuresService } from './client-features.service';
@Injectable()
export class Client_featuresPopupService {
    private isOpen = false;
    constructor (
        private modalService: NgbModal,
        private router: Router,
        private client_featuresService: Client_featuresService
    ) {}

    open (component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.client_featuresService.find(id).subscribe(client_features => {
                this.client_featuresModalRef(component, client_features);
            });
        } else {
            return this.client_featuresModalRef(component, new Client_features());
        }
    }

    client_featuresModalRef(component: Component, client_features: Client_features): NgbModalRef {
        let modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.client_features = client_features;
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
