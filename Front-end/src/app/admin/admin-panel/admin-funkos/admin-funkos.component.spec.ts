import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminFunkosComponent } from './admin-funkos.component';

describe('AdminFunkosComponent', () => {
  let component: AdminFunkosComponent;
  let fixture: ComponentFixture<AdminFunkosComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AdminFunkosComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdminFunkosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
