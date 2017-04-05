import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { MockBackend } from '@angular/http/testing';
import { Http, BaseRequestOptions } from '@angular/http';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils } from 'ng-jhipster';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ClientCategoryDetailComponent } from '../../../../../../main/webapp/app/entities/client-category/client-category-detail.component';
import { ClientCategoryService } from '../../../../../../main/webapp/app/entities/client-category/client-category.service';
import { ClientCategory } from '../../../../../../main/webapp/app/entities/client-category/client-category.model';

describe('Component Tests', () => {

    describe('ClientCategory Management Detail Component', () => {
        let comp: ClientCategoryDetailComponent;
        let fixture: ComponentFixture<ClientCategoryDetailComponent>;
        let service: ClientCategoryService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                declarations: [ClientCategoryDetailComponent],
                providers: [
                    MockBackend,
                    BaseRequestOptions,
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    {
                        provide: Http,
                        useFactory: (backendInstance: MockBackend, defaultOptions: BaseRequestOptions) => {
                            return new Http(backendInstance, defaultOptions);
                        },
                        deps: [MockBackend, BaseRequestOptions]
                    },
                    ClientCategoryService
                ]
            }).overrideComponent(ClientCategoryDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ClientCategoryDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ClientCategoryService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN
            spyOn(service, 'find').and.returnValue(Observable.of(new ClientCategory(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.clientCategory).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
