import { Injectable } from '@angular/core';
import { Http, Response, Headers } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { Book } from "./book";

@Injectable()
export class BookService {
  private basePageUrl = "http://localhost:8080/api/pages/?id=";
  private baseSortUrl = "http://localhost:8080/api/sort";
  private baseSearchUrl = "http://localhost:8080/api/search/?query=";

   constructor(private http: Http) {
   }

   getBook(): Observable<Book[]> {
        return this.http.get("http://localhost:8080/api/app")
         .map((res: Response) => res.json() as Book[])
         .catch((error: any) => Observable.throw(error.json().error || 'Server error'));
   }

   getPage(page: number): Promise<Book[]> {
     const url = `${this.basePageUrl}${page}`;
     return this.http.get(url)
     .toPromise()
     .then(response => response.json())
     .catch(this.handleError);
   }

   getTotalBooks(): Promise<number> {
     return this.http.get("http://localhost:8080/api/booksTotal")
      .toPromise()
      .then(response => response.json())
      .catch(this.handleError);
   }

//http://localhost:8080/api/sort?type=title&order=ascending&pageId=0
   getBooksSorted(type: string, order: string, pageId: number): Observable<Book[]> {
     const typeUrl = "?type=";
     const orderUrl = "&order=";
     const pageUrl = "&pageId=";
     const url = `${this.baseSortUrl}${typeUrl}${type}${orderUrl}${order}${pageUrl}${pageId}`;
     console.log(url);
     return this.http.get(url)
     .map((res: Response) => res.json() as Book[])
     .catch((error: any) => Observable.throw(error.json().error || 'Server error'));
   }

  searchBooks(query: string): Promise<Book[]> {
   const url = `${this.baseSearchUrl}${query}`;
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
