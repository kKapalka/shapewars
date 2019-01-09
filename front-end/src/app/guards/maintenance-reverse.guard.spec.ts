import { TestBed, async, inject } from '@angular/core/testing';

import { MaintenanceReverseGuard } from './maintenance-reverse.guard';

describe('MaintenanceReverseGuard', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [MaintenanceReverseGuard]
    });
  });

  it('should ...', inject([MaintenanceReverseGuard], (guard: MaintenanceReverseGuard) => {
    expect(guard).toBeTruthy();
  }));
});
