import { Client } from '../client';
import { ThirdPartyFeature } from '../third-party-feature';
export class ClientFeatures {
    constructor(
        public id?: number,
        public featureUrl?: string,
        public price?: number,
        public client?: Client,
        public thirdPartyFeature?: ThirdPartyFeature,
    ) { }
}
