import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { InactiveSiteComponent } from './inactive-site.component';

describe('InactiveSiteComponent', () => {
  let component: InactiveSiteComponent;
  let fixture: ComponentFixture<InactiveSiteComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ InactiveSiteComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(InactiveSiteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
