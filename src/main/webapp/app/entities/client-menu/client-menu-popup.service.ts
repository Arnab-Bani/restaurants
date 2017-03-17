import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { ClientMenu } from './client-menu.model';
import { ClientMenuService } from './client-menu.service';
@Injectable()
export class ClientMenuPopupService {
    private isOpen = false;
    constructor (
        private modalService: NgbModal,
        private router: Router,
        private clientMenuService: ClientMenuService
    ) {}

    open (component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.clientMenuService.find(id).subscribe(clientMenu => {
                this.clientMenuModalRef(component, clientMenu);
            });
        } else {
            return this.clientMenuModalRef(component, new ClientMenu());
        }
    }

    clientMenuModalRef(component: Component, clientMenu: ClientMenu): NgbModalRef {
        let modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.clientMenu = clientMenu;
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
