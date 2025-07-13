import { Component } from '@angular/core';
import {Router, RouterOutlet} from '@angular/router';
import {MatSidenavContainer, MatSidenavModule} from '@angular/material/sidenav';
import {MatToolbar} from '@angular/material/toolbar';
import {MatNavList} from '@angular/material/list';
import {MatIcon} from '@angular/material/icon';

@Component({
  selector: 'app-layout',
  templateUrl: './layout.component.html',
  imports: [
    MatSidenavContainer,
    MatToolbar,
    MatNavList,
    RouterOutlet,
    MatIcon,
    MatSidenavModule
  ],
  styleUrls: ['./layout.component.scss']
})
export class LayoutComponent {
  constructor(private router: Router) {}

  logout(): void {
    localStorage.removeItem('token');
    this.router.navigate(['/login']);
  }
}
