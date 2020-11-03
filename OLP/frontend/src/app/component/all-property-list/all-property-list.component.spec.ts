import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AllPropertyListComponent } from './all-property-list.component';

describe('AllPropertyListComponent', () => {
  let component: AllPropertyListComponent;
  let fixture: ComponentFixture<AllPropertyListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AllPropertyListComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AllPropertyListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
