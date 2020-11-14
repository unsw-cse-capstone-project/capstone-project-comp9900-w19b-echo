import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BidHistoryComponent } from './bid-history.component';

describe('BidHistoryComponent', () => {
  let component: BidHistoryComponent;
  let fixture: ComponentFixture<BidHistoryComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BidHistoryComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BidHistoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
