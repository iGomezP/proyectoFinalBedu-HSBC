import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-footer',
  templateUrl: './footer.component.html',
  styleUrls: ['./footer.component.scss'],
})
export class FooterComponent implements OnInit {
  private currentDate = new Date();
  currentYear!: string;

  constructor() {}

  ngOnInit(): void {
    this.currentYear = this.currentDate.getFullYear().toString();
  }
}
