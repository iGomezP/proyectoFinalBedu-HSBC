import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SignupDireccionComponent } from './signup-direccion.component';

describe('SignupDireccionComponent', () => {
  let component: SignupDireccionComponent;
  let fixture: ComponentFixture<SignupDireccionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SignupDireccionComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SignupDireccionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
