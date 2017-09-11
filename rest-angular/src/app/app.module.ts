import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';

import { AppComponent } from './app.component';
import { BookService } from './book.service';
import { MainComponent } from './main.component';
import { AppRoutingModule } from './app-routing.module';
import { BookDetailsComponent } from './book-details.component';
import { BookDetailsService } from './book-details.service';


@NgModule({
  declarations: [
    AppComponent,
    MainComponent,
    BookDetailsComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    AppRoutingModule
  ],
  providers: [
    BookService,
    BookDetailsService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
