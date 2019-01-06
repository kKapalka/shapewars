import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ShapeInventoryComponent } from './shape-inventory.component';

describe('ShapeInventoryComponent', () => {
  let component: ShapeInventoryComponent;
  let fixture: ComponentFixture<ShapeInventoryComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ShapeInventoryComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ShapeInventoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
