import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InterestedPropertyCardComponent } from './interested-property-card.component';

describe('InterestedPropertyCardComponent', () => {
  let component: InterestedPropertyCardComponent;
  let fixture: ComponentFixture<InterestedPropertyCardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ InterestedPropertyCardComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(InterestedPropertyCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
