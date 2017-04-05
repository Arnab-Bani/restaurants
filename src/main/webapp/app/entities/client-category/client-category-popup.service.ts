import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { ClientCategory } from './client-category.model';
import { ClientCategoryService } from './client-category.service';
@Injectable()
export class ClientCategoryPopupService {
    private isOpen = false;
    constructor (
        private modalService: NgbModal,
        private router: Router,
        private clientCategoryService: ClientCategoryService
    ) {}

    open (component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.clientCategoryService.find(id).subscribe(clientCategory => {
                this.clientCategoryModalRef(component, clientCategory);
            });
        } else {
            return this.clientCategoryModalRef(component, new ClientCategory());
        }
    }

    clientCategoryModalRef(component: Component, clientCategory: ClientCategory): NgbModalRef {
        let modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.clientCategory = clientCategory;
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
