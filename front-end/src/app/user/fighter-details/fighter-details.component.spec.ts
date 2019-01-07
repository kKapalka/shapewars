import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FighterDetailsComponent } from './fighter-details.component';

describe('FighterDetailsComponent', () => {
  let component: FighterDetailsComponent;
  let fixture: ComponentFixture<FighterDetailsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FighterDetailsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FighterDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
