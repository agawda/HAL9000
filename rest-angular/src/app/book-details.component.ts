import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, ParamMap } from '@angular/router';

import { BookDetailsService } from './book-details.service';
import { Book } from './book';

import 'rxjs/add/operator/switchMap';

@Component({
  selector: 'book-details',
  templateUrl: './book-details.component.html',
  styleUrls: ['./book-details.component.css']
})
export class BookDetailsComponent implements OnInit {
  title = 'app';
  book: Book;
  promoDetails: number;
  Math: any;

  constructor(private bookDetailsService: BookDetailsService, private route: ActivatedRoute) {
    this.Math = Math;
  }

  ngOnInit(): void {
    this.route.paramMap.switchMap((params: ParamMap) =>
      this.bookDetailsService.getBook(+params.get('id')))
      .subscribe(book => this.book = book);
      console.log(this.book);
  }
}
