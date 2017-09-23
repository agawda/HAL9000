import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { Book } from './book';
import { Config } from './config/config';

@Injectable()
export class BookDetailsService {
  private baseUrl: string;
  private booksApiURL: string;

   constructor(private http: Http, private _config: Config) {
     this.baseUrl = _config.get('apiUrl');
     this.booksApiURL = _config.get('bookDetailUrl');
   }

   getBook(id: number): Observable<Book> {
     const url = `${this.baseUrl}${this.booksApiURL}${id}`;
     console.log(url);
        return this.http.get(url)
         .map((res: Response) => res.json() as Book)
         .catch((error: any) => Observable.throw(error.json().error || 'Server error'));
   }

   private handleError(error: any): Promise<any> {
    console.error('An error occured', error);
    return Promise.reject(error.message || error);
  }
}
