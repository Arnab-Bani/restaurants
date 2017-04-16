import { ClientCategory } from '../client-category';
export class ClientMenu {
    constructor(
        public id?: number,
        public item_id?: number,
        public item_name?: string,
        public item_image?: any,
        public item_price?: number,
        public item_desc?: string,
        public clientCategory?: ClientCategory,
    ) { }
}
