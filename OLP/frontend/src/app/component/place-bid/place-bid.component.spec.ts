import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PlaceBidComponent } from './place-bid.component';

describe('PlaceBidComponent', () => {
  let component: PlaceBidComponent;
  let fixture: ComponentFixture<PlaceBidComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PlaceBidComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PlaceBidComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
