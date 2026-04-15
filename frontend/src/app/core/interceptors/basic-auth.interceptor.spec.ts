/// <reference types="vitest" />

import { HttpClient, provideHttpClient, withInterceptors } from '@angular/common/http';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';
import { basicAuthInterceptor } from './basic-auth.interceptor';
import { AuthSessionService } from '../services/auth-session.service';

describe('basicAuthInterceptor', () => {
  let httpClient: HttpClient;
  let httpMock: HttpTestingController;
  let authSessionService: AuthSessionService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        AuthSessionService,
        provideHttpClient(withInterceptors([basicAuthInterceptor])),
        provideHttpClientTesting(),
      ],
    });

    httpClient = TestBed.inject(HttpClient);
    httpMock = TestBed.inject(HttpTestingController);
    authSessionService = TestBed.inject(AuthSessionService);
    sessionStorage.clear();
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('adds Authorization header when credentials exist', () => {
    authSessionService.setCredentials('admin', 'admin123');

    httpClient.get('/secured').subscribe();

    const req = httpMock.expectOne('/secured');
    expect(req.request.headers.get('Authorization')).toBe('Basic YWRtaW46YWRtaW4xMjM=');
    req.flush({});
  });

  it('does not add Authorization header when credentials are missing', () => {
    httpClient.get('/public').subscribe();

    const req = httpMock.expectOne('/public');
    expect(req.request.headers.has('Authorization')).toBe(false);
    req.flush({});
  });
});
