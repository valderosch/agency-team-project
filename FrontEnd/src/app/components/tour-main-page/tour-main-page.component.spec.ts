import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TourMainPageComponent } from './tour-main-page.component';

describe('TourMainPageComponent', () => {
  let component: TourMainPageComponent;
  let fixture: ComponentFixture<TourMainPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TourMainPageComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TourMainPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
