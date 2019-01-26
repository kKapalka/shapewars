import { TestBed } from '@angular/core/testing';

import { InterceptorAuthService } from './interceptor-auth.service';

describe('InterceptorAuthService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: InterceptorAuthService = TestBed.get(InterceptorAuthService);
    expect(service).toBeTruthy();
  });
});
