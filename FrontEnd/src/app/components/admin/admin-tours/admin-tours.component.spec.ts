import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminToursComponent } from './admin-tours.component';

describe('AdminToursComponent', () => {
  let component: AdminToursComponent;
  let fixture: ComponentFixture<AdminToursComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AdminToursComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AdminToursComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
