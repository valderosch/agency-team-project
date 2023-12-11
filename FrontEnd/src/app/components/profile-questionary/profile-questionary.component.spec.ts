import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProfileQuestionaryComponent } from './profile-questionary.component';

describe('ProfileQuestionaryComponent', () => {
  let component: ProfileQuestionaryComponent;
  let fixture: ComponentFixture<ProfileQuestionaryComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ProfileQuestionaryComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ProfileQuestionaryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
