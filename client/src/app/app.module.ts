import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { provideHttpClient } from '@angular/common/http';
import { ReactiveFormsModule } from '@angular/forms';

import { AppComponent } from './app.component';
import { MenuComponent } from './components/menu.component';
import { PlaceOrderComponent } from './components/place-order.component';

import { ConfirmationComponent } from './components/confirmation.component';
import { RouterModule, RouterOutlet, Routes } from '@angular/router';
import { RestaurantService } from './restaurant.service';
import { CartStore } from './cart.store';

const appRoutes: Routes = [
  { path: '', component: MenuComponent },
  // { path: 'category/:category', component: CategoryComponent }, 
  // { path: 'checkout', component: ConfirmCheckoutComponent, canActivate: [authGuard] },
  { path: '**', redirectTo: '' }
]

@NgModule({
  declarations: [
    AppComponent, MenuComponent, PlaceOrderComponent, ConfirmationComponent
  ],
  imports: [
    BrowserModule, ReactiveFormsModule, RouterModule.forRoot(appRoutes, { useHash: true })
  ],
  providers: [ provideHttpClient(), RestaurantService, CartStore ],
  bootstrap: [AppComponent]
})
export class AppModule { }
