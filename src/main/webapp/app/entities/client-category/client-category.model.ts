import { Client } from '../client';
import { Category } from '../category';
export class ClientCategory {
    constructor(
        public id?: number,
        public client?: Client,
        public category?: Category,
    ) { }
}
