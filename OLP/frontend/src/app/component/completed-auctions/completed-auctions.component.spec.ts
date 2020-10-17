import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CompletedAuctionsComponent } from './completed-auctions.component';

describe('CompletedAuctionsComponent', () => {
  let component: CompletedAuctionsComponent;
  let fixture: ComponentFixture<CompletedAuctionsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CompletedAuctionsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CompletedAuctionsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
