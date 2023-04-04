import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddDevToTicketComponent } from './add-dev-to-ticket.component';

describe('AddDevToTicketComponent', () => {
  let component: AddDevToTicketComponent;
  let fixture: ComponentFixture<AddDevToTicketComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AddDevToTicketComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddDevToTicketComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
