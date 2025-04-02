import {Component} from '@angular/core';
import { RouterOutlet } from '@angular/router';
import {AsyncPipe} from '@angular/common';
import {NavbarComponent} from './shared/navbar/navbar.component';


@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, AsyncPipe, NavbarComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
  showNavbar = true;



}

