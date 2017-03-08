import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ClientFeatures } from './client-features.model';
import { ClientFeaturesService } from './client-features.service';

@Component({
    selector: 'jhi-client-features-detail',
    templateUrl: './client-features-detail.component.html'
})
export class ClientFeaturesDetailComponent implements OnInit, OnDestroy {

    clientFeatures: ClientFeatures;
    private subscription: any;

    constructor(
        private clientFeaturesService: ClientFeaturesService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.clientFeaturesService.find(id).subscribe(clientFeatures => {
            this.clientFeatures = clientFeatures;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
