import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { BookService } from './book.service';
import { Book } from './book';

import 'rxjs/add/operator/debounceTime';
import 'rxjs/add/operator/distinctUntilChanged';


@Component({
  selector: 'app-books',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css']
})
export class MainComponent {
  title = 'books';

  books: Book[];
  Math: any;
  isSortedByTitle: boolean;
  isSortedByAuthor: boolean;
  isSortedByPrice: boolean;
  isSortedByPromo: boolean;


  constructor(private bookService: BookService, private router: Router) {
    bookService.getBook().subscribe(books => this.books = books);
    this.Math = Math;
    this.isSortedByTitle = true;
    this.isSortedByAuthor = true;
    this.isSortedByPrice = true;
    this.isSortedByPromo = true;
  }

  searchBooks(param: string) {
    this.bookService.searchBooks(param).then(books => {
      this.books = books;
    });
  }

  sortBy(param: string) {
    this.bookService.getBooksSorted(param).subscribe(books => this.books = books);
  }

  sortByTitle() {
    if (this.isSortedByTitle) {
      this.books.sort((a: any, b: any) => {
        if (a.title < b.title) {
          return -1;
        } else if (a.title > b.title) {
          return 1;
        } else {
          return 0;
        }
      });
      this.isSortedByTitle = false;
    } else {
      this.books.sort((a: any, b: any) => {
        if (a.title < b.title) {
          return 1;
        } else if (a.title > b.title) {
          return -1;
        } else {
          return 0;
        }
      });
      this.isSortedByTitle = true;
    }
  }

  sortByAuthor() {
    if (this.isSortedByAuthor) {
      this.books.sort((a: any, b: any) => {
        if (a.authors[0] < b.authors[0]) {
          return -1;
        } else if (a.authors[0] > b.authors[0]) {
          return 1;
        } else {
          return 0;
        }
      });
      this.isSortedByAuthor = false;
    } else {
      this.books.sort((a: any, b: any) => {
        if (a.authors[0] < b.authors[0]) {
          return 1;
        } else if (a.authors[0] > b.authors[0]) {
          return -1;
        } else {
          return 0;
        }
      });
      this.isSortedByAuthor = true;
    }
  }

  sortByPrice() {
    if (this.isSortedByPrice) {
      this.books.sort((a: any, b: any) => {
        if (a.retailPriceAmount < b.retailPriceAmount) {
          return -1;
        } else if (a.retailPriceAmount > b.retailPriceAmount) {
          return 1;
        } else {
          return 0;
        }
      });
      this.isSortedByPrice = false;
    } else {
      this.books.sort((a: any, b: any) => {
        if (a.retailPriceAmount < b.retailPriceAmount) {
          return 1;
        } else if (a.retailPriceAmount > b.retailPriceAmount) {
          return -1;
        } else {
          return 0;
        }
      });
      this.isSortedByPrice = true;
    }
  }

  sortByPromo() {
    if (this.isSortedByPromo) {
      this.books.sort((a: any, b: any) => {
        if(a.listPriceAmount === 0) return -1;
        if(b.listPriceAmount === 0) return 1;
        const aPromo = 100 - (a.retailPriceAmount * 100 / a.listPriceAmount);
        const bPromo = 100 - (b.retailPriceAmount * 100 / b.listPriceAmount);
        if (aPromo < bPromo) {
          return -1;
        } else if (aPromo > bPromo) {
          return 1;
        } else {
          return 0;
        }
      });
      this.isSortedByPromo = false;
    } else {
      this.books.sort((a: any, b: any) => {
        if(a.listPriceAmount === 0) return 1;
        if(b.listPriceAmount === 0) return -1;
        const aPromo = 100 - (a.retailPriceAmount * 100 / a.listPriceAmount);
        const bPromo = 100 - (b.retailPriceAmount * 100 / b.listPriceAmount);
        if (aPromo < bPromo) {
          return 1;
        } else if (aPromo > bPromo) {
          return -1;
        } else {
          return 0;
        }
      });
      this.isSortedByPromo = true;
    }
  }

  reset() {
    this.bookService.getBook().subscribe(books => this.books = books);
  }
}
