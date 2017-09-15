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
  currentPage: number;
  maxPages: number;


  constructor(private bookService: BookService, private router: Router) {
    this.Math = Math;
    this.isSortedByTitle = true;
    this.isSortedByAuthor = true;
    this.isSortedByPrice = true;
    this.isSortedByPromo = true;
    this.currentPage = 0;
    bookService.getTotalBooks().then(maxBooks => {
      this.maxPages = Math.ceil(maxBooks/20);
    });
    bookService.getPage(this.currentPage).then(books => this.books = books);
  }

  nextPage() {
    if(this.currentPage === this.maxPages) return;
    this.currentPage++;
    this.bookService.getPage(this.currentPage).then(books => this.books = books);
    console.log(this.currentPage);
  }

  previousPage() {
    if(this.currentPage === 0) return;
    this.currentPage--;
    this.bookService.getPage(this.currentPage).then(books => this.books = books);
  }

  searchBooks(param: string) {
    this.bookService.searchBooks(param).then(books => {
      this.books = books;
    });
  }

  sortByTitle() {
    if (this.isSortedByTitle) {
      this.sortRequestAscending("title");
      this.isSortedByTitle = false;
    } else {
      this.sortRequestDescending("title");
      this.isSortedByTitle = true;
    }
  }

  sortByAuthor() {
    if (this.isSortedByAuthor) {
      this.sortRequestAscending("authors");
      this.isSortedByAuthor = false;
    } else {
      this.sortRequestDescending("authors");
      this.isSortedByAuthor = true;
    }
  }

  sortByPrice() {
    if (this.isSortedByPrice) {
      this.sortRequestAscending("price");
      this.isSortedByPrice = false;
    } else {
      this.sortRequestDescending("price");
      this.isSortedByPrice = true;
    }
  }

  sortByPromo() {
    if (this.isSortedByPromo) {
      this.sortRequestAscending("discount");
      this.isSortedByPromo = false;
    } else {
      this.sortRequestDescending("discount");
      this.isSortedByPromo = true;
    }
  }

  private sortRequestAscending(param: string) {
    this.bookService.getBooksSorted(param, "ascending", this.currentPage).subscribe(books => this.books = books);
  }

  private sortRequestDescending(param: string) {
    this.bookService.getBooksSorted(param, "descending", this.currentPage).subscribe(books => this.books = books);
  }

  reset() {
    this.bookService.getPage(this.currentPage).then(books => this.books = books);
  }
}
