import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { ClientCategory } from './client-category.model';
import { ClientCategoryPopupService } from './client-category-popup.service';
import { ClientCategoryService } from './client-category.service';

@Component({
    selector: 'jhi-client-category-delete-dialog',
    templateUrl: './client-category-delete-dialog.component.html'
})
export class ClientCategoryDeleteDialogComponent {

    client_category: ClientCategory;

    constructor(
        private client_categoryService: ClientCategoryService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear () {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete (id: number) {
        this.client_categoryService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'client_categoryListModification',
                content: 'Deleted an client_category'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-client-category-delete-popup',
    template: ''
})
export class ClientCategoryDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private client_categoryPopupService: ClientCategoryPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.client_categoryPopupService
                .open(ClientCategoryDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
