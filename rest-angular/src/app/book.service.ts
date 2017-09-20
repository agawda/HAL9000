import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { Book } from "./book";

@Injectable()
export class BookService {
  private basePageUrl = "http://localhost:10520/api/pages/?id=";
  private baseSortUrl = "http://localhost:10520/api/sort";
  private baseSearchUrl = "http://localhost:10520/api/search/?query=";
  private baseAdvancedSearchUrl = "http://localhost:10520/api/advancedSearch";
  private baseMaxBooksUrl = "http://localhost:10520/api/booksTotal";

   constructor(private http: Http) {}

   getPage(page: number): Promise<Book[]> {
     const url = `${this.basePageUrl}${page}`;
     return this.http.get(url)
     .toPromise()
     .then(response => response.json())
     .catch(this.handleError);
   }

   getTotalBooks(): Promise<number> {
     return this.http.get(this.baseMaxBooksUrl)
      .toPromise()
      .then(response => response.json())
      .catch(this.handleError);
   }

   getBooksSorted(type: string, order: string, pageId: number): Observable<Book[]> {
     const typeUrl = "?type=";
     const orderUrl = "&order=";
     const pageUrl = "&pageId=";
     const url = `${this.baseSortUrl}${typeUrl}${type}${orderUrl}${order}${pageUrl}${pageId}`;
     return this.http.get(url)
     .map((res: Response) => res.json() as Book[])
     .catch((error: any) => Observable.throw(error.json().error || 'Server error'));
   }

  searchBooks(query: string): Promise<Book[]> {
   const url = `${this.baseSearchUrl}${query}`;
   return this.http.get(url)
    .toPromise().then(response => response.json())
    .catch(this.handleError);
  }

  advancedSearchBooks(title: string, author: string, category: string, bookstore: string, min: number, max: number): Promise<Book[]> {
    const titleP = "?title=";
    const authorP = "&author=";
    const categoryP = "&category=";
    const bookstoreP = "&bookstore=";
    const minP = "&minPrice=";
    const maxP = "&maxPrice=";
    const url = `${this.baseAdvancedSearchUrl}${titleP}${title}${authorP}${author}${categoryP}${category}${bookstoreP}${bookstore}${minP}${min}${maxP}${max}`;
    console.log(url);
    return this.http.get(url)
      .toPromise().then(response => response.json())
      .catch(this.handleError);
  }

   private handleError(error: any): Promise<any> {
    console.error('An error occured', error);
    return Promise.reject(error.message || error);
  }
}
