import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ActiveAuctionsComponent } from './active-auctions.component';

describe('ActiveAuctionsComponent', () => {
  let component: ActiveAuctionsComponent;
  let fixture: ComponentFixture<ActiveAuctionsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ActiveAuctionsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ActiveAuctionsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
