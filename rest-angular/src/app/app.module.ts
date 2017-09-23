import { BrowserModule } from '@angular/platform-browser';
import { NgModule, APP_INITIALIZER } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';

import { AppComponent } from './app.component';
import { BookService } from './book.service';
import { MainComponent } from './main.component';
import { AppRoutingModule } from './app-routing.module';
import { BookDetailsComponent } from './book-details.component';
import { BookDetailsService } from './book-details.service';
import { Config } from './config/config';

export function initConfig(config: Config){
 return () => config.load()
}

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
    BookDetailsService,
    Config,
    { provide: APP_INITIALIZER,
         useFactory: initConfig,
         deps: [Config],
         multi: true }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
