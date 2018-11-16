import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ShapePanelComponent } from './shape-panel.component';

describe('ShapePanelComponent', () => {
  let component: ShapePanelComponent;
  let fixture: ComponentFixture<ShapePanelComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ShapePanelComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ShapePanelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
