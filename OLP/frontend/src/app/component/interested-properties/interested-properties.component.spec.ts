import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InterestedPropertiesComponent } from './interested-properties.component';

describe('InterestedPropertiesComponent', () => {
  let component: InterestedPropertiesComponent;
  let fixture: ComponentFixture<InterestedPropertiesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ InterestedPropertiesComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(InterestedPropertiesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
