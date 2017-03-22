import { Component, OnInit } from '@angular/core';
import { Response } from '@angular/http';
import { ITEMS_PER_PAGE, Principal, AccountService } from '../shared';
import { ActivatedRoute, Router } from '@angular/router';
import { ClientMenu } from '../entities/client-menu/client-menu.model';
import { ClientMenuService } from '../entities/client-menu/client-menu.service';
import { EventManager, ParseLinks, PaginationUtil, AlertService, DataUtils } from 'ng-jhipster';

import { PaginationConfig } from '../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-settings',
    templateUrl: './menu.component.html'
})
export class MenuComponent implements OnInit {
    clientMenus: ClientMenu[];
    error: string;
    success: string;
    settingsAccount: any;
    languages: any[];
    links: any;
    totalItems: any;
    queryCount: any;
    itemsPerPage: any;
    page: any;
    predicate: any;
    previousPage: any;
    reverse: any;
    routeData: any;

    constructor(
        private clientMenuService: ClientMenuService,
        private activatedRoute: ActivatedRoute,
        private parseLinks: ParseLinks,
         private alertService: AlertService
    ) {
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.routeData = this.activatedRoute.data.subscribe(data => {
            this.page = data['pagingParams'].page;
            this.previousPage = data['pagingParams'].page;
            this.reverse = data['pagingParams'].ascending;
            this.predicate = data['pagingParams'].predicate;
        });
    }

    ngOnInit () {
        this.page = 2;
        this.loadAll();
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
