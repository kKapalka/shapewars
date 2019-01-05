import { TestBed } from '@angular/core/testing';

import { IntervalManagerService } from './interval-manager.service';

describe('IntervalManagerService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: IntervalManagerService = TestBed.get(IntervalManagerService);
    expect(service).toBeTruthy();
  });
});
