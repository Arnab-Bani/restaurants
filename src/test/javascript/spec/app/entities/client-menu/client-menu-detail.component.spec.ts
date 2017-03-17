import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { MockBackend } from '@angular/http/testing';
import { Http, BaseRequestOptions } from '@angular/http';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils } from 'ng-jhipster';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ClientMenuDetailComponent } from '../../../../../../main/webapp/app/entities/client-menu/client-menu-detail.component';
import { ClientMenuService } from '../../../../../../main/webapp/app/entities/client-menu/client-menu.service';
import { ClientMenu } from '../../../../../../main/webapp/app/entities/client-menu/client-menu.model';

describe('Component Tests', () => {

    describe('ClientMenu Management Detail Component', () => {
        let comp: ClientMenuDetailComponent;
        let fixture: ComponentFixture<ClientMenuDetailComponent>;
        let service: ClientMenuService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                declarations: [ClientMenuDetailComponent],
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
                    ClientMenuService
                ]
            }).overrideComponent(ClientMenuDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ClientMenuDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ClientMenuService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN
            spyOn(service, 'find').and.returnValue(Observable.of(new ClientMenu(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.clientMenu).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
