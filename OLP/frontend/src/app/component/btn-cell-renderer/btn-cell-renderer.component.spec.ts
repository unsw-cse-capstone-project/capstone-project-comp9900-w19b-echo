import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BtnCellRenderer } from './btn-cell-renderer.component';

describe('BtnCellRendererComponent', () => {
  let component: BtnCellRenderer;
  let fixture: ComponentFixture<BtnCellRenderer>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BtnCellRenderer ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BtnCellRenderer);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
