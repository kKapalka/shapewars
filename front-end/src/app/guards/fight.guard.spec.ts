import { TestBed, async, inject } from '@angular/core/testing';

import { FightGuard } from './fight.guard';

describe('FightGuard', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [FightGuard]
    });
  });

  it('should ...', inject([FightGuard], (guard: FightGuard) => {
    expect(guard).toBeTruthy();
  }));
});
