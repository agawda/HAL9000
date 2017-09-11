import { Component } from '@angular/core';
import { BookService } from './book.service';
import { Book } from './book';
import { MainComponent } from './main.component';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'app';
}
