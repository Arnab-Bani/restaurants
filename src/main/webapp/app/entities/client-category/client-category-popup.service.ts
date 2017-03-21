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
        private client_categoryService: ClientCategoryService
    ) {}

    open (component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.client_categoryService.find(id).subscribe(client_category => {
                this.client_categoryModalRef(component, client_category);
            });
        } else {
            return this.client_categoryModalRef(component, new ClientCategory());
        }
    }

    client_categoryModalRef(component: Component, client_category: ClientCategory): NgbModalRef {
        let modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.client_category = client_category;
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
