import { Injectable } from '@angular/core';
import { Http, Response, Headers } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { Book } from "./book";

@Injectable()
export class BookService {
  private baseUrl = "http://localhost:8080/api/sorted/?sorted=";
   constructor(private http: Http) {
   }

   getBook(): Observable<Book[]> {
        return this.http.get("http://localhost:8080/api/app")
        // .toPromise().then(response => {
        //   console.log(response);
        //   return response.json().data as Greeting[];
        // })
        // .catch(this.handleError);
         .map((res: Response) => res.json() as Book[])
         .catch((error: any) => Observable.throw(error.json().error || 'Server error'));
   }

   getBooksSorted(param: string): Observable<Book[]> {
     const url = `${this.baseUrl}${param}`;
     console.log(url);
     return this.http.get(url)
     .map((res: Response) => res.json() as Book[])
     .catch((error: any) => Observable.throw(error.json().error || 'Server error'));
   }

   getBooksSortedTitleAscending(): Observable<Book[]> {
     const baseUrl = "http://localhost:8080/api/sorted/?sorted=";
     return this.http.get("http://localhost:8080/api/sorted/?sorted=titleAsc")
      .map((res: Response) => res.json() as Book[])
      .catch((error: any) => Observable.throw(error.json().error || 'Server error'));
   }

   getBooksSortedTitleDescending(): Observable<Book[]> {
     return this.http.get("http://localhost:8080/api/sorted/?sorted=titleDsc")
      .map((res: Response) => res.json() as Book[])
      .catch((error: any) => Observable.throw(error.json().error || 'Server error'));
   }

   private handleError(error: any): Promise<any> {
    console.error('An error occured', error);
    return Promise.reject(error.message || error);
  }
}
