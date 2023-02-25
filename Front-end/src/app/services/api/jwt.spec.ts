import { TestBed } from '@angular/core/testing';
import jwt_decode from 'jwt-decode';

import { JwtService } from './jwt.service';

describe('LocalStorageService', () => {
  let service: JwtService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(JwtService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
