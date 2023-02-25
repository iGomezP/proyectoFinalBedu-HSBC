import { Component, OnInit } from '@angular/core';
import { FunkosService } from 'src/app/services/api/funkos.service';
import { JwtService } from 'src/app/services/api/jwt.service';

@Component({
  selector: 'app-productos',
  templateUrl: './productos.component.html',
  styleUrls: ['./productos.component.scss'],
})
export class ProductosComponent {
  public allFunkosData: any;
  public funkoImage: string = '';
  private validateToken!: boolean;
  private validateRol!: string;

  constructor(
    private funkosService: FunkosService,
    private tokenService: JwtService
  ) {}

  getAllFunkos() {
    this.validateToken = this.tokenService.validateTokenExpiration(
      this.tokenService.getJwtToken()
    );
    this.validateRol = this.tokenService.getUserRole();
    if (!this.validateToken) {
      if (this.validateRol === 'ADMIN' || this.validateRol === 'USER') {
        this.funkosService.getAllFunkosService().subscribe({
          next: (data) => {
            this.allFunkosData = data;
            this.funkoImage = data[0].awsImageFunko.awsUrl;
            console.log(this.allFunkosData);
            console.log(this.funkoImage);
            //console.log(data);
          },
          error: (error) => console.log(error),
        });
      }
      // Redirigir a login con mensaje
    }
  }

  ngOnInit(): void {
    this.getAllFunkos();
  }
}
