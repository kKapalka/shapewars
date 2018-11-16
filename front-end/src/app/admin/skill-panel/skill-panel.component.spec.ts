import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SkillPanelComponent } from './skill-panel.component';

describe('SkillPanelComponent', () => {
  let component: SkillPanelComponent;
  let fixture: ComponentFixture<SkillPanelComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SkillPanelComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SkillPanelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
