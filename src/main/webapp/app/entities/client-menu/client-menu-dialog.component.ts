import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, DataUtils } from 'ng-jhipster';

import { ClientMenu } from './client-menu.model';
import { ClientMenuPopupService } from './client-menu-popup.service';
import { ClientMenuService } from './client-menu.service';
@Component({
    selector: 'jhi-client-menu-dialog',
    templateUrl: './client-menu-dialog.component.html'
})
export class ClientMenuDialogComponent implements OnInit {

    clientMenu: ClientMenu;
    authorities: any[];
    isSaving: boolean;
    constructor(
        public activeModal: NgbActiveModal,
        private dataUtils: DataUtils,
        private alertService: AlertService,
        private clientMenuService: ClientMenuService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
    }
    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData($event, clientMenu, field, isImage) {
        if ($event.target.files && $event.target.files[0]) {
            let $file = $event.target.files[0];
            if (isImage && !/^image\//.test($file.type)) {
                return;
            }
            this.dataUtils.toBase64($file, (base64Data) => {
                clientMenu[field] = base64Data;
                clientMenu[`${field}ContentType`] = $file.type;
            });
        }
    }
    clear () {
        this.activeModal.dismiss('cancel');
    }

    save () {
        this.isSaving = true;
        if (this.clientMenu.id !== undefined) {
            this.clientMenuService.update(this.clientMenu)
                .subscribe((res: ClientMenu) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.clientMenuService.create(this.clientMenu)
                .subscribe((res: ClientMenu) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess (result: ClientMenu) {
        this.eventManager.broadcast({ name: 'clientMenuListModification', content: 'OK'});
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
    selector: 'jhi-client-menu-popup',
    template: ''
})
export class ClientMenuPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private clientMenuPopupService: ClientMenuPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.clientMenuPopupService
                    .open(ClientMenuDialogComponent, params['id']);
            } else {
                this.modalRef = this.clientMenuPopupService
                    .open(ClientMenuDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
