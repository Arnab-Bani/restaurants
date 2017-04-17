import { Injectable, Inject } from '@angular/core';
import { Http, Response, URLSearchParams, BaseRequestOptions } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { ClientCategory } from './client-category.model';
import { RiyazConstants } from '../client/riyaz.constants';
@Injectable()
export class ClientCategoryService {

    private resourceUrl = 'api/client-categories';

    constructor(@Inject(RiyazConstants) private riyazConstants,
    private http: Http) { }

    create(clientCategory: ClientCategory): Observable<ClientCategory> {
        let copy: ClientCategory = Object.assign({}, clientCategory);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(clientCategory: ClientCategory): Observable<ClientCategory> {
        let copy: ClientCategory = Object.assign({}, clientCategory);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<ClientCategory> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            return res.json();
        });
    }

    query(req?: any): Observable<Response> {
        let options = this.createRequestOption(req);
        return this.http.get(this.resourceUrl+'/clientId/'+this.riyazConstants.clientId, options)
        ;
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }



    private createRequestOption(req?: any): BaseRequestOptions {
        let options: BaseRequestOptions = new BaseRequestOptions();
        if (req) {
            let params: URLSearchParams = new URLSearchParams();
            params.set('page', req.page);
            params.set('size', req.size);
            if (req.sort) {
                params.paramsMap.set('sort', req.sort);
            }
            params.set('query', req.query);

            options.search = params;
        }
        return options;
    }
}
