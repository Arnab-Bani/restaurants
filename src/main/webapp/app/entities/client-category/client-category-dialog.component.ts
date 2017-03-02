import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { ClientCategory } from './client-category.model';
import { ClientCategoryPopupService } from './client-category-popup.service';
import { ClientCategoryService } from './client-category.service';
import { Client, ClientService } from '../client';
import { Category, CategoryService } from '../category';
@Component({
    selector: 'jhi-client-category-dialog',
    templateUrl: './client-category-dialog.component.html'
})
export class ClientCategoryDialogComponent implements OnInit {

    clientCategory: ClientCategory;
    authorities: any[];
    isSaving: boolean;

    clients: Client[];

    categories: Category[];
    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private clientCategoryService: ClientCategoryService,
        private clientService: ClientService,
        private categoryService: CategoryService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.clientService.query().subscribe(
            (res: Response) => { this.clients = res.json(); }, (res: Response) => this.onError(res.json()));
        this.categoryService.query().subscribe(
            (res: Response) => { this.categories = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    clear () {
        this.activeModal.dismiss('cancel');
    }

    save () {
        this.isSaving = true;
        if (this.clientCategory.id !== undefined) {
            this.clientCategoryService.update(this.clientCategory)
                .subscribe((res: ClientCategory) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.clientCategoryService.create(this.clientCategory)
                .subscribe((res: ClientCategory) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess (result: ClientCategory) {
        this.eventManager.broadcast({ name: 'clientCategoryListModification', content: 'OK'});
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

    trackClientById(index: number, item: Client) {
        return item.id;
    }

    trackCategoryById(index: number, item: Category) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-client-category-popup',
    template: ''
})
export class ClientCategoryPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private clientCategoryPopupService: ClientCategoryPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.clientCategoryPopupService
                    .open(ClientCategoryDialogComponent, params['id']);
            } else {
                this.modalRef = this.clientCategoryPopupService
                    .open(ClientCategoryDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
