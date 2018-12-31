import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddEditColormapComponent } from './add-edit-colormap.component';

describe('AddEditColormapComponent', () => {
  let component: AddEditColormapComponent;
  let fixture: ComponentFixture<AddEditColormapComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddEditColormapComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddEditColormapComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
