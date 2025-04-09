import {Component, ViewChild} from '@angular/core';
import {Router, RouterLink, RouterLinkActive, RouterOutlet} from '@angular/router';
import {AsyncPipe, NgOptimizedImage} from "@angular/common";
import {MatIcon, MatIconRegistry} from "@angular/material/icon";
import {MatToolbar} from "@angular/material/toolbar";
import {AuthService} from '../../core/services/auth/auth.service';
import {DomSanitizer} from '@angular/platform-browser';
import {MessageService} from '../../core/services/message/message.service';
import {MESSAGES} from '../../core/messages/messages';
import {MatSidenav, MatSidenavContainer} from '@angular/material/sidenav';

@Component({
  selector: 'app-main-layout',
  standalone: true,
  imports: [
    RouterOutlet,
    AsyncPipe,
    MatIcon,
    MatToolbar,
    NgOptimizedImage,
    RouterLink,
    RouterLinkActive,
    MatSidenavContainer,
    MatSidenav
  ],
  templateUrl: './main-layout.component.html',
  styleUrl: './main-layout.component.scss'
})
export class MainLayoutComponent {
  @ViewChild('sidenav') sidenav!: MatSidenav;

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
    void this.sidenav.toggle();
  }

  onLogout() {
    this.authService.logout();
    void this.router.navigate(['']);
    this.messageService.showInfo(MESSAGES.LOGOUT_SUCCESS);
  }

}
