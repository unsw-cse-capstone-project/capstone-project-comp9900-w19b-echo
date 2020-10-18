import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NewPropertyComponent } from './new-property.component';

describe('NewPropertyComponent', () => {
  let component: NewPropertyComponent;
  let fixture: ComponentFixture<NewPropertyComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ NewPropertyComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(NewPropertyComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
