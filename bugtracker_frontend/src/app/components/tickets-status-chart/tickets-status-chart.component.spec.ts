import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TicketsStatusChartComponent } from './tickets-status-chart.component';

describe('TicketsStatusChartComponent', () => {
  let component: TicketsStatusChartComponent;
  let fixture: ComponentFixture<TicketsStatusChartComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TicketsStatusChartComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TicketsStatusChartComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
