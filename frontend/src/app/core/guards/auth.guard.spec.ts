/// <reference types="vitest" />

import { TestBed } from '@angular/core/testing';
import { Router, UrlTree } from '@angular/router';
import { authGuard } from './auth.guard';
import { AuthSessionService } from '../services/auth-session.service';

describe('authGuard', () => {
  it('allows navigation when authenticated', () => {
    const authStub = {
      isAuthenticated: () => true,
    } as AuthSessionService;

    const routerStub = {
      createUrlTree: () => ({}) as UrlTree,
    } as unknown as Router;

    TestBed.configureTestingModule({
      providers: [
        { provide: AuthSessionService, useValue: authStub },
        { provide: Router, useValue: routerStub },
      ],
    });

    const result = TestBed.runInInjectionContext(() => authGuard({} as never, {} as never));
    expect(result).toBe(true);
  });

  it('redirects to /login when unauthenticated', () => {
    const authStub = {
      isAuthenticated: () => false,
    } as AuthSessionService;

    const tree = {} as UrlTree;
    const routerStub = {
      createUrlTree: (commands: string[]) => {
        expect(commands).toEqual(['/login']);
        return tree;
      },
    } as unknown as Router;

    TestBed.configureTestingModule({
      providers: [
        { provide: AuthSessionService, useValue: authStub },
        { provide: Router, useValue: routerStub },
      ],
    });

    const result = TestBed.runInInjectionContext(() => authGuard({} as never, {} as never));
    expect(result).toBe(tree);
  });
});
