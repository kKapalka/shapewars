import { TestBed, async, inject } from '@angular/core/testing';

import { MaintenanceFightGuard } from './maintenance-fight.guard';

describe('MaintenanceFightGuard', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [MaintenanceFightGuard]
    });
  });

  it('should ...', inject([MaintenanceFightGuard], (guard: MaintenanceFightGuard) => {
    expect(guard).toBeTruthy();
  }));
});
