import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TitulationFormDialogComponent } from './titulation-form-dialog.component';

describe('TitulationFormDialogComponent', () => {
  let component: TitulationFormDialogComponent;
  let fixture: ComponentFixture<TitulationFormDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TitulationFormDialogComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TitulationFormDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
