import { Component, OnInit, OnDestroy } from '@angular/core';
import { Response } from '@angular/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, AlertService, DataUtils } from 'ng-jhipster';

import { ClientMenu } from './client-menu.model';
import { ClientMenuService } from './client-menu.service';
import { ITEMS_PER_PAGE, Principal } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-client-menu',
    templateUrl: './client-menu.component.html'
})
export class ClientMenuComponent implements OnInit, OnDestroy {

currentAccount: any;
    clientMenus: ClientMenu[];
    error: any;
    success: any;
    eventSubscriber: Subscription;
    routeData: any;
    links: any;
    totalItems: any;
    queryCount: any;
    itemsPerPage: any;
    page: any;
    predicate: any;
    previousPage: any;
    reverse: any;

    constructor(
        private clientMenuService: ClientMenuService,
        private parseLinks: ParseLinks,
        private alertService: AlertService,
        private principal: Principal,
        private activatedRoute: ActivatedRoute,
        private dataUtils: DataUtils,
        private router: Router,
        private eventManager: EventManager,
        private paginationUtil: PaginationUtil,
        private paginationConfig: PaginationConfig
    ) {
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.routeData = this.activatedRoute.data.subscribe(data => {
            this.page = data['pagingParams'].page;
            this.previousPage = data['pagingParams'].page;
            this.reverse = data['pagingParams'].ascending;
            this.predicate = data['pagingParams'].predicate;
        });
    }

    loadAll() {
        this.clientMenuService.query({
            page: this.page - 1,
            size: this.itemsPerPage,
            sort: this.sort()}).subscribe(
            (res: Response) => this.onSuccess(res.json(), res.headers),
            (res: Response) => this.onError(res.json())
        );
    }
    loadPage (page: number) {
        if (page !== this.previousPage) {
            this.previousPage = page;
            this.transition();
        }
    }
    transition() {
        this.router.navigate(['/client-menu'], {queryParams:
            {
                page: this.page,
                size: this.itemsPerPage,
                sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
            }
        });
        this.loadAll();
    }

    clear() {
        this.page = 0;
        this.router.navigate(['/client-menu', {
            page: this.page,
            sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
        }]);
        this.loadAll();
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInClientMenus();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId (index: number, item: ClientMenu) {
        return item.id;
    }



    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    registerChangeInClientMenus() {
        this.eventSubscriber = this.eventManager.subscribe('clientMenuListModification', (response) => this.loadAll());
    }

    sort () {
        let result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    private onSuccess (data, headers) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = headers.get('X-Total-Count');
        this.queryCount = this.totalItems;
        // this.page = pagingParams.page;
        this.clientMenus = data;
    }

    private onError (error) {
        this.alertService.error(error.message, null, null);
    }
}
