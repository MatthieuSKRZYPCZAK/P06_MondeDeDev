import { Component } from '@angular/core';
import {MatToolbar} from '@angular/material/toolbar';
import {Router, RouterLink, RouterLinkActive} from '@angular/router';
import {MatIcon} from '@angular/material/icon';
import {AuthService} from '../../core/services/auth/auth.service';
import {MessageService} from '../../core/services/message/message.service';
import {MESSAGES} from '../../core/messages/messages';
import {AsyncPipe, NgOptimizedImage} from '@angular/common';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [
    MatToolbar,
    RouterLink,
    MatIcon,
    RouterLinkActive,
    AsyncPipe,
    NgOptimizedImage
  ],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.scss'
})
export class NavbarComponent {

  constructor(
    private router: Router,
    protected authService: AuthService,
    private messageService: MessageService
  ) {}

  shouldHideNavbar(): boolean {
    return ['/login', '/register'].includes(this.router.url);
  }

  onLogout() {
    this.authService.logout();
    void this.router.navigate(['']);
    this.messageService.showInfo(MESSAGES.LOGOUT_SUCCESS);
  }
}
