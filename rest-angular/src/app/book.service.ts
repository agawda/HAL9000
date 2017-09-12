import { Injectable } from '@angular/core';
import { Http, Response, Headers } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { Book } from "./book";

@Injectable()
export class BookService {
  private baseSortUrl = "http://localhost:8080/api/sorted/?sorted=";
  private baseSearchUrl = "http://localhost:8080/api/search/?query=";

   constructor(private http: Http) {
   }

   getBook(): Observable<Book[]> {
        return this.http.get("http://localhost:8080/api/app")
         .map((res: Response) => res.json() as Book[])
         .catch((error: any) => Observable.throw(error.json().error || 'Server error'));
   }

   getBooksSorted(param: string): Observable<Book[]> {
     const url = `${this.baseSortUrl}${param}`;
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
