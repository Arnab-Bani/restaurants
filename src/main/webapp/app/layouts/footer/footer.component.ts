import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ClientFeatures } from '../../entities/client-features/client-features.model';
import { ClientFeaturesService } from '../../entities/client-features/client-features.service';

@Component({
    selector: 'jhi-footer',
    templateUrl: './footer.component.html'
})
export class FooterComponent implements OnInit, OnDestroy {
    clientFeatures: ClientFeatures;
    facebookUrl: string;
    foursquareUrl: string;
    yelpUrl: string;
    private subscription: any;

    constructor(
        private clientFeaturesService: ClientFeaturesService,
        private route: ActivatedRoute
    ) { }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load('1');
        });
    }

    load (id) {

        this.clientFeaturesService.getClientFeaturesByClientId(id).subscribe(clientFeatures => {
            this.clientFeatures = clientFeatures;
            for (let i in clientFeatures) {
                if (clientFeatures[i]!.thirdPartyFeature!.featureCode === "FB") {
                    this.facebookUrl = clientFeatures[i].featureUrl;
                } else if (clientFeatures[i]!.thirdPartyFeature!.featureCode === "yelp") {
                    this.yelpUrl = clientFeatures[i].featureUrl;
                } else if (clientFeatures[i]!.thirdPartyFeature!.featureCode === "foursquare") {
                    this.foursquareUrl = clientFeatures[i].featureUrl;
                }
            }
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }
}
