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

  private TITLE = "title";
  private AUTHOR = "authors";
  private PRICE = "price";
  private PROMO = "discount";
  private ASC = "ascending";
  private DSC = "descending";
  private NONE = "none";

  books: Book[];
  Math: any;
  sortBy: string; //title, author, price, promo
  orderBy: string; //ascending, descending, null

  currentPage: number;
  maxPages: number;

  constructor(private bookService: BookService, private router: Router) {
    this.Math = Math;

    this.orderBy = this.NONE;
    this.sortBy = this.NONE;
    this.currentPage = 0;

    bookService.getTotalBooks().then(maxBooks => {
      this.maxPages = Math.ceil(maxBooks/20);
    });
    bookService.getPage(this.currentPage).then(books => this.books = books);
  }

  nextPage() {
    if(this.currentPage === this.maxPages) return;
    this.currentPage++;
    if(this.sortBy !== this.NONE) this.sortRequest();
    else this.getRequest();
    console.log(this.currentPage);
  }

  previousPage() {
    if(this.currentPage === 0) return;
    this.currentPage--;
    if(this.sortBy !== this.NONE) this.sortRequest();
    else this.getRequest();
  }

  searchBooks(param: string) {
    this.bookService.searchBooks(param).then(books => {
      this.books = books;
    });
  }

  sortByTitle() {
    this.checkIfSorted(this.TITLE);
    this.sortBy = this.TITLE;
    if(this.orderBy === this.NONE || this.orderBy === this.DSC) {
      this.orderBy = this.ASC;
    } else if(this.orderBy === this.ASC) {
      this.orderBy = this.DSC;
    }
    this.sortRequest();
  }

  sortByAuthor() {
    this.checkIfSorted(this.AUTHOR);
    this.sortBy = this.AUTHOR;
    if(this.orderBy === this.NONE || this.orderBy === this.DSC) {
      this.orderBy = this.ASC;
    } else if(this.orderBy === this.ASC) {
      this.orderBy = this.DSC;
    }
    this.sortRequest();
  }

  sortByPrice() {
    this.checkIfSorted(this.PRICE);
    this.sortBy = this.PRICE;
    if(this.orderBy === this.NONE || this.orderBy === this.DSC) {
      this.orderBy = this.ASC;
    } else if(this.orderBy === this.ASC) {
      this.orderBy = this.DSC;
    }
    this.sortRequest();
  }

  sortByPromo() {
    this.checkIfSorted(this.PROMO);
    this.sortBy = this.PROMO;
    if(this.orderBy === this.NONE || this.orderBy === this.DSC) {
      this.orderBy = this.ASC;
    } else if(this.orderBy === this.ASC) {
      this.orderBy = this.DSC;
    }
    this.sortRequest();
  }

  private sortRequest() {
    this.bookService.getBooksSorted(this.sortBy, this.orderBy, this.currentPage).subscribe(books => this.books = books);
  }

  private getRequest() {
    this.bookService.getPage(this.currentPage).then(books => this.books = books);
  }

  private checkIfSorted(param: string) {
    if(this.sortBy !== param && this.orderBy !== this.NONE) this.orderBy = this.NONE;
  }

  reset() {
    this.getRequest();
    this.sortBy = this.NONE;
    this.orderBy = this.NONE;
  }
}
