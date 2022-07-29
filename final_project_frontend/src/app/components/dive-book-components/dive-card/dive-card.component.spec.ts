import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DiveCardComponent } from './dive-card.component';

describe('DiveCardComponent', () => {
  let component: DiveCardComponent;
  let fixture: ComponentFixture<DiveCardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DiveCardComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DiveCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
