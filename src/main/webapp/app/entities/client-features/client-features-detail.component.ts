import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Client_features } from './client-features.model';
import { Client_featuresService } from './client-features.service';

@Component({
    selector: 'jhi-client-features-detail',
    templateUrl: './client-features-detail.component.html'
})
export class Client_featuresDetailComponent implements OnInit, OnDestroy {

    client_features: Client_features;
    private subscription: any;

    constructor(
        private client_featuresService: Client_featuresService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.client_featuresService.find(id).subscribe(client_features => {
            this.client_features = client_features;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
