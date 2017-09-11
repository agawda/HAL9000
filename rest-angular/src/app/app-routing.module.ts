import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { MainComponent } from './main.component';
import { BookDetailsComponent } from './book-details.component';


const routes: Routes = [
  { path: '', component: MainComponent },
  { path: 'books/:id', component: BookDetailsComponent}
]
@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
