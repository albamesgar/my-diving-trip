import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DiveInfoDialogComponent } from './dive-info-dialog.component';

describe('DiveInfoDialogComponent', () => {
  let component: DiveInfoDialogComponent;
  let fixture: ComponentFixture<DiveInfoDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DiveInfoDialogComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DiveInfoDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
