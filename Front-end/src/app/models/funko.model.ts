import { AwsImageFunko } from './imagenFunko';

export interface FunkoModel {
  name: string;
  price: number;
  stock: number;
  layaway: number;
  awsImageFunko: AwsImageFunko;
}
