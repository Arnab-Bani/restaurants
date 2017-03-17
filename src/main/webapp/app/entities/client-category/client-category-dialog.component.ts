import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { Client_category } from './client-category.model';
import { Client_categoryPopupService } from './client-category-popup.service';
import { Client_categoryService } from './client-category.service';
@Component({
    selector: 'jhi-client-category-dialog',
    templateUrl: './client-category-dialog.component.html'
})
export class Client_categoryDialogComponent implements OnInit {

    client_category: Client_category;
    authorities: any[];
    isSaving: boolean;
    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private client_categoryService: Client_categoryService,
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
        if (this.client_category.id !== undefined) {
            this.client_categoryService.update(this.client_category)
                .subscribe((res: Client_category) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.client_categoryService.create(this.client_category)
                .subscribe((res: Client_category) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess (result: Client_category) {
        this.eventManager.broadcast({ name: 'client_categoryListModification', content: 'OK'});
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
    selector: 'jhi-client-category-popup',
    template: ''
})
export class Client_categoryPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private client_categoryPopupService: Client_categoryPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.client_categoryPopupService
                    .open(Client_categoryDialogComponent, params['id']);
            } else {
                this.modalRef = this.client_categoryPopupService
                    .open(Client_categoryDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
