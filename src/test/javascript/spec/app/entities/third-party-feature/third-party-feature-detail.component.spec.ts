import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { MockBackend } from '@angular/http/testing';
import { Http, BaseRequestOptions } from '@angular/http';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils } from 'ng-jhipster';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ThirdPartyFeatureDetailComponent } from '../../../../../../main/webapp/app/entities/third-party-feature/third-party-feature-detail.component';
import { ThirdPartyFeatureService } from '../../../../../../main/webapp/app/entities/third-party-feature/third-party-feature.service';
import { ThirdPartyFeature } from '../../../../../../main/webapp/app/entities/third-party-feature/third-party-feature.model';

describe('Component Tests', () => {

    describe('ThirdPartyFeature Management Detail Component', () => {
        let comp: ThirdPartyFeatureDetailComponent;
        let fixture: ComponentFixture<ThirdPartyFeatureDetailComponent>;
        let service: ThirdPartyFeatureService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                declarations: [ThirdPartyFeatureDetailComponent],
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
                    ThirdPartyFeatureService
                ]
            }).overrideComponent(ThirdPartyFeatureDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ThirdPartyFeatureDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ThirdPartyFeatureService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN
            spyOn(service, 'find').and.returnValue(Observable.of(new ThirdPartyFeature(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.thirdPartyFeature).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
