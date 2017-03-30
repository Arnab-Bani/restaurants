import { Client } from '../client';
import { ThirdPartyFeature } from '../third-party-feature';
export class ClientFeatures {
    constructor(
        public id?: number,
        public featureUrl?: string,
        public price?: number,
        public clientId?: Client,
        public thirdPartyFeatureId?: ThirdPartyFeature,
    ) { }
}
