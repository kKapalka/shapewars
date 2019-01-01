import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { XpthresholdPanelComponent } from './xpthreshold-panel.component';

describe('XpthresholdPanelComponent', () => {
  let component: XpthresholdPanelComponent;
  let fixture: ComponentFixture<XpthresholdPanelComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ XpthresholdPanelComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(XpthresholdPanelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
