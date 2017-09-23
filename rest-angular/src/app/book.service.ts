import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { Book } from './book';
import { Config } from './config/config';

@Injectable()
export class BookService {
  private baseUrl: string;
  private basePageUrl: string;
  private baseSortUrl: string;
  private baseSearchUrl: string;
  private baseAdvancedSearchUrl: string;
  private baseMaxBooksUrl: string;

  constructor(private http: Http, private _config: Config) {
    this.baseUrl = _config.get('apiUrl');
    this.basePageUrl = _config.get('pagesUrl');
    this.baseSortUrl = _config.get('sortUrl');
    this.baseSearchUrl = _config.get('searchUrl');
    this.baseAdvancedSearchUrl = _config.get('advancedSearchUrl');
    this.baseMaxBooksUrl = _config.get('maxBooksUrl');
  }

  getPage(page: number): Promise<Book[]> {
    const url = `${this.baseUrl}${this.basePageUrl}${page}`;
    return this.http.get(url)
      .toPromise()
      .then(response => response.json())
      .catch(this.handleError);
  }

  getTotalBooks(): Promise<number> {
    const url = `${this.baseUrl}${this.baseMaxBooksUrl}`;
    return this.http.get(url)
      .toPromise()
      .then(response => response.json())
      .catch(this.handleError);
  }

  getBooksSorted(type: string, order: string, pageId: number): Observable<Book[]> {
    const typeUrl = "?type=";
    const orderUrl = "&order=";
    const pageUrl = "&pageId=";
    const url = `${this.baseUrl}${this.baseSortUrl}${typeUrl}${type}${orderUrl}${order}${pageUrl}${pageId}`;
    return this.http.get(url)
      .map((res: Response) => res.json() as Book[])
      .catch((error: any) => Observable.throw(error.json().error || 'Server error'));
  }

  searchBooks(query: string): Observable<Book[]> {
    const url = `${this.baseUrl}${this.baseSearchUrl}${query}`;
    return this.http.get(url)
      .map((res: Response) => res.json() as Book[])
      .catch((error: any) => Observable.throw(error.json().error || 'Server error'));
  }

  advancedSearchBooks(title: string, author: string, category: string, bookstore: string, min: number, max: number): Promise<Book[]> {
    const titleP = "?title=";
    const authorP = "&author=";
    const categoryP = "&category=";
    const bookstoreP = "&bookstore=";
    const minP = "&minPrice=";
    const maxP = "&maxPrice=";
    const url = `${this.baseUrl}${this.baseAdvancedSearchUrl}${titleP}${title}${authorP}${author}${categoryP}${category}${bookstoreP}${bookstore}${minP}${min}${maxP}${max}`;
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
