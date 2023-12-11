import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProfileOrderHistoryComponent } from './profile-order-history.component';

describe('ProfileOrderHistoryComponent', () => {
  let component: ProfileOrderHistoryComponent;
  let fixture: ComponentFixture<ProfileOrderHistoryComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ProfileOrderHistoryComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ProfileOrderHistoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
