import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';

@Component({
  selector: 'app-feed',
  standalone: true,
  imports: [],
  templateUrl: './feed.component.html',
  styleUrl: './feed.component.scss'
})
export class FeedComponent {

  constructor(
    private route: ActivatedRoute,
    private router: Router,
  ) {
  }


}
