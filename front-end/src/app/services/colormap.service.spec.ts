import { TestBed, inject } from '@angular/core/testing';

import { ColormapService } from './colormap.service';

describe('ColormapService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [ColormapService]
    });
  });

  it('should be created', inject([ColormapService], (service: ColormapService) => {
    expect(service).toBeTruthy();
  }));
});
