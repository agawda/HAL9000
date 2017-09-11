import { Injectable } from '@angular/core';
import { Http, Response, Headers } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { Book } from "./book";

@Injectable()
export class BookDetailsService {
  private booksApiURL = "http://localhost:8080/api/books?id=";
   constructor(private http: Http) {
   }

   getBook(id: number): Observable<Book> {
     const url = `${this.booksApiURL}${id}`;
     console.log(url);
        return this.http.get(url)
        // .toPromise().then(response => {
        //   console.log(response);
        //   return response.json().data as Book;
        // })
        // .catch(this.handleError);
         .map((res: Response) => res.json() as Book)
         .catch((error: any) => Observable.throw(error.json().error || 'Server error'));
   }

   private handleError(error: any): Promise<any> {
    console.error('An error occured', error);
    return Promise.reject(error.message || error);
  }
}
