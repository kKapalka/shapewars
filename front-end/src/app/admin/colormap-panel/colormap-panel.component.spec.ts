import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ColormapPanelComponent } from './colormap-panel.component';

describe('ColormapPanelComponent', () => {
  let component: ColormapPanelComponent;
  let fixture: ComponentFixture<ColormapPanelComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ColormapPanelComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ColormapPanelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
