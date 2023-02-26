import { TestBed } from '@angular/core/testing';

import { EnvironmentFactoryService } from './environment-factory.service';

describe('EnvironmentFactoryService', () => {
  let service: EnvironmentFactoryService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EnvironmentFactoryService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
