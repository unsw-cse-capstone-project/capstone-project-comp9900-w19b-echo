import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MyPropertyCardComponent } from './my-property-card.component';

describe('MyPropertyCardComponent', () => {
  let component: MyPropertyCardComponent;
  let fixture: ComponentFixture<MyPropertyCardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MyPropertyCardComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MyPropertyCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
