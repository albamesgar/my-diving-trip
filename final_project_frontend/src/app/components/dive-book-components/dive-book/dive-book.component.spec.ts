import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DiveBookComponent } from './dive-book.component';

describe('DiveBookComponent', () => {
  let component: DiveBookComponent;
  let fixture: ComponentFixture<DiveBookComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DiveBookComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DiveBookComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
