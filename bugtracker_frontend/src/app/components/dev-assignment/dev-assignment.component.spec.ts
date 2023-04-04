import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DevAssignmentComponent } from './dev-assignment.component';

describe('DevAssignmentComponent', () => {
  let component: DevAssignmentComponent;
  let fixture: ComponentFixture<DevAssignmentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DevAssignmentComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DevAssignmentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
