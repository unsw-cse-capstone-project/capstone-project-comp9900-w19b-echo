import { ComponentFixture, TestBed } from '@angular/core/testing';

import { JoinBidComponent } from './join-bid.component';

describe('JoinBidComponent', () => {
  let component: JoinBidComponent;
  let fixture: ComponentFixture<JoinBidComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ JoinBidComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(JoinBidComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
