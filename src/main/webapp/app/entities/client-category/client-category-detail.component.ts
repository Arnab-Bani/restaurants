import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ClientCategory } from './client-category.model';
import { ClientCategoryService } from './client-category.service';

@Component({
    selector: 'jhi-client-category-detail',
    templateUrl: './client-category-detail.component.html'
})
export class ClientCategoryDetailComponent implements OnInit, OnDestroy {

    client_category: ClientCategory;
    private subscription: any;

    constructor(
        private client_categoryService: ClientCategoryService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.client_categoryService.find(id).subscribe(client_category => {
            this.client_category = client_category;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
