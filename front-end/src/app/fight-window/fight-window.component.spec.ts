import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FightWindowComponent } from './fight-window.component';

describe('FightWindowComponent', () => {
  let component: FightWindowComponent;
  let fixture: ComponentFixture<FightWindowComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FightWindowComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FightWindowComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
