import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { MockBackend } from '@angular/http/testing';
import { Http, BaseRequestOptions } from '@angular/http';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils } from 'ng-jhipster';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { Client_featuresDetailComponent } from '../../../../../../main/webapp/app/entities/client-features/client-features-detail.component';
import { Client_featuresService } from '../../../../../../main/webapp/app/entities/client-features/client-features.service';
import { Client_features } from '../../../../../../main/webapp/app/entities/client-features/client-features.model';

describe('Component Tests', () => {

    describe('Client_features Management Detail Component', () => {
        let comp: Client_featuresDetailComponent;
        let fixture: ComponentFixture<Client_featuresDetailComponent>;
        let service: Client_featuresService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                declarations: [Client_featuresDetailComponent],
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
                    Client_featuresService
                ]
            }).overrideComponent(Client_featuresDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(Client_featuresDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(Client_featuresService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN
            spyOn(service, 'find').and.returnValue(Observable.of(new Client_features(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.client_features).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
