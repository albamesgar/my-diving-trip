import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TitulationCardComponent } from './titulation-card.component';

describe('TitulationCardComponent', () => {
  let component: TitulationCardComponent;
  let fixture: ComponentFixture<TitulationCardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TitulationCardComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TitulationCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
