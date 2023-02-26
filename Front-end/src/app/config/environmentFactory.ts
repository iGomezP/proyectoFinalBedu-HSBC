import { environment } from 'src/environments/environment';
import { environment as prodEnvironment } from 'src/environments/environment.prod';

export function environmentFactory() {
  if (environment.production) {
    return prodEnvironment;
  } else {
    return environment;
  }
}
