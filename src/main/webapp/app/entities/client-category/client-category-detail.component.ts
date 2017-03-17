import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Client_category } from './client-category.model';
import { Client_categoryService } from './client-category.service';

@Component({
    selector: 'jhi-client-category-detail',
    templateUrl: './client-category-detail.component.html'
})
export class Client_categoryDetailComponent implements OnInit, OnDestroy {

    client_category: Client_category;
    private subscription: any;

    constructor(
        private client_categoryService: Client_categoryService,
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
