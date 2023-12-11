import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProfileEditUserComponent } from './profile-edit-user.component';

describe('ProfileEditUserComponent', () => {
  let component: ProfileEditUserComponent;
  let fixture: ComponentFixture<ProfileEditUserComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ProfileEditUserComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ProfileEditUserComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
