import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DiveFormDialogComponent } from './dive-form-dialog.component';

describe('DiveFormDialogComponent', () => {
  let component: DiveFormDialogComponent;
  let fixture: ComponentFixture<DiveFormDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DiveFormDialogComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DiveFormDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
