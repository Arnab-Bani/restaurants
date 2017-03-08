import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { MockBackend } from '@angular/http/testing';
import { Http, BaseRequestOptions } from '@angular/http';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils } from 'ng-jhipster';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ClientFeaturesDetailComponent } from '../../../../../../main/webapp/app/entities/client-features/client-features-detail.component';
import { ClientFeaturesService } from '../../../../../../main/webapp/app/entities/client-features/client-features.service';
import { ClientFeatures } from '../../../../../../main/webapp/app/entities/client-features/client-features.model';

describe('Component Tests', () => {

    describe('ClientFeatures Management Detail Component', () => {
        let comp: ClientFeaturesDetailComponent;
        let fixture: ComponentFixture<ClientFeaturesDetailComponent>;
        let service: ClientFeaturesService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                declarations: [ClientFeaturesDetailComponent],
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
                    ClientFeaturesService
                ]
            }).overrideComponent(ClientFeaturesDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ClientFeaturesDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ClientFeaturesService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN
            spyOn(service, 'find').and.returnValue(Observable.of(new ClientFeatures(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.clientFeatures).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
