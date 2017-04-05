import { Client } from '../client';
export class ClientCategory {
    constructor(
        public id?: number,
        public categoryName?: string,
        public client?: Client,
    ) { }
}
