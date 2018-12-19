import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddEditShapeComponent } from './add-edit-shape.component';

describe('AddEditShapeComponent', () => {
  let component: AddEditShapeComponent;
  let fixture: ComponentFixture<AddEditShapeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddEditShapeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddEditShapeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
