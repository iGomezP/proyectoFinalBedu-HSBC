import { TestBed } from '@angular/core/testing';

import { GlobalErrorsInterceptor } from './global-errors.interceptor';

describe('GlobalErrorsInterceptor', () => {
  beforeEach(() => TestBed.configureTestingModule({
    providers: [
      GlobalErrorsInterceptor
      ]
  }));

  it('should be created', () => {
    const interceptor: GlobalErrorsInterceptor = TestBed.inject(GlobalErrorsInterceptor);
    expect(interceptor).toBeTruthy();
  });
});
