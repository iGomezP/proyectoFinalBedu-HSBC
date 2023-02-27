import { Component } from '@angular/core';
import { FormBuilder, FormGroup, NgForm } from '@angular/forms';
import { FunkoModel } from 'src/app/models/funko.model';

@Component({
  selector: 'app-admin-funkos',
  templateUrl: './admin-funkos.component.html',
  styleUrls: ['./admin-funkos.component.scss'],
})
export class AdminFunkosComponent {
  formFunko!: FormGroup;
  constructor(private formBuilder: FormBuilder) {
    this.formFunko = this.formBuilder.group({
      name: [''],
      price: [''],
      stock: [''],
      layaway: [''],
      imageFunko: [''],
    });
  }
  imageName = '';

  onFileSelected(event: any) {
    this.imageName = event.target.files[0].name;
    this.formFunko.get('imageFunko')?.setValue(event.target.files[0]);
    console.log(this.formFunko.get('imageFunko'));
  }

  registrarFunko(form: NgForm) {
    const formData = new FormData();
    formData.append('dataFunko', JSON.stringify(this.formFunko.value));
    formData.append('imageFunko', this.formFunko.get('imageFunko')?.value);
    console.log(`Registrando producto: ${formData}`);
  }
}
