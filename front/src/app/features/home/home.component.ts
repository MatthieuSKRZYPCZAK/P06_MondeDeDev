import {Component} from '@angular/core';
import {MatButton} from '@angular/material/button';
import {RouterLink} from '@angular/router';
import {NgOptimizedImage} from '@angular/common';


@Component({
  selector: 'app-home',
  standalone: true,
  imports: [
    MatButton,
    RouterLink,
    NgOptimizedImage,
  ],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent  {

}
