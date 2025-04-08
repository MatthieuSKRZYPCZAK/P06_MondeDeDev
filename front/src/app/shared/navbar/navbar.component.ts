import {Component, ViewChild} from '@angular/core';
import {MatToolbar} from '@angular/material/toolbar';
import {Router, RouterLink, RouterLinkActive} from '@angular/router';
import {MatIcon, MatIconRegistry} from '@angular/material/icon';
import {AuthService} from '../../core/services/auth/auth.service';
import {MessageService} from '../../core/services/message/message.service';
import {MESSAGES} from '../../core/messages/messages';
import {AsyncPipe, NgOptimizedImage} from '@angular/common';
import {DomSanitizer} from '@angular/platform-browser';
import {MatSidenav, MatSidenavContainer} from '@angular/material/sidenav';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [
    MatToolbar,
    RouterLink,
    MatIcon,
    RouterLinkActive,
    AsyncPipe,
    NgOptimizedImage,
    MatSidenavContainer,
    MatSidenav,
  ],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.scss'
})
export class NavbarComponent {

  // @ViewChild('sidenav') sidenav!: MatSidenav;

  constructor(
    private router: Router,
    protected authService: AuthService,
    private matIconRegistry: MatIconRegistry,
    private domSanitizer: DomSanitizer,
    private messageService: MessageService
  ) {
    this.matIconRegistry.addSvgIcon('profil-active', this.domSanitizer.bypassSecurityTrustResourceUrl('assets/icons/profile-active.svg'));
    this.matIconRegistry.addSvgIcon('profil', this.domSanitizer.bypassSecurityTrustResourceUrl('assets/icons/profile.svg'));
  }

  isProfileRoute(): boolean {
    return this.router.url === '/me';
  }

  shouldHideNavbar(): boolean {
    return ['/login', '/register'].includes(this.router.url);
  }

  toggleSidenav() {

  }

  onLogout() {
    this.authService.logout();
    void this.router.navigate(['']);
    this.messageService.showInfo(MESSAGES.LOGOUT_SUCCESS);
  }
}
