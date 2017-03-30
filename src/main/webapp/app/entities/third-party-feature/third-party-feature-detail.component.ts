import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ThirdPartyFeature } from './third-party-feature.model';
import { ThirdPartyFeatureService } from './third-party-feature.service';

@Component({
    selector: 'jhi-third-party-feature-detail',
    templateUrl: './third-party-feature-detail.component.html'
})
export class ThirdPartyFeatureDetailComponent implements OnInit, OnDestroy {

    thirdPartyFeature: ThirdPartyFeature;
    private subscription: any;

    constructor(
        private thirdPartyFeatureService: ThirdPartyFeatureService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.thirdPartyFeatureService.find(id).subscribe(thirdPartyFeature => {
            this.thirdPartyFeature = thirdPartyFeature;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
