import { Injectable } from '@angular/core';
import {
  environment as devEnvironment,
  environment,
} from 'src/environments/environment';
import { environment as prodEnvironment } from 'src/environments/environment.prod';

@Injectable({
  providedIn: 'root',
})
export class EnvironmentFactoryService {
  constructor() {}

  getEnvironment() {
    if (environment.production) {
      return prodEnvironment;
    } else {
      return devEnvironment;
    }
  }
}
