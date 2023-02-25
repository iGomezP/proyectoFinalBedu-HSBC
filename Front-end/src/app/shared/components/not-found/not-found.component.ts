import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-not-found',
  templateUrl: './not-found.component.html',
  styleUrls: ['./not-found.component.scss'],
})
export class NotFoundComponent implements OnInit {
  constructor(private router: Router) {}

  contador = 5;

  redirectPage() {
    setTimeout(() => {
      this.router.navigate(['']);
    }, 5000);
  }

  ngOnInit(): void {
    this.redirectPage();
  }
}
