import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ClientCategory } from './client-category.model';
import { ClientCategoryService } from './client-category.service';

@Component({
    selector: 'jhi-client-category-detail',
    templateUrl: './client-category-detail.component.html'
})
export class ClientCategoryDetailComponent implements OnInit, OnDestroy {

    clientCategory: ClientCategory;
    private subscription: any;

    constructor(
        private clientCategoryService: ClientCategoryService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.clientCategoryService.find(id).subscribe(clientCategory => {
            this.clientCategory = clientCategory;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
