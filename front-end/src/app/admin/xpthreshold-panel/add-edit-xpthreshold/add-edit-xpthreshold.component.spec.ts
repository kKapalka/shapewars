import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddEditXpthresholdComponent } from './add-edit-xpthreshold.component';

describe('AddEditXpthresholdComponent', () => {
  let component: AddEditXpthresholdComponent;
  let fixture: ComponentFixture<AddEditXpthresholdComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddEditXpthresholdComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddEditXpthresholdComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
