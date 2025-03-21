import { Component, inject, OnInit } from '@angular/core';
import { CartStore } from '../cart.store';
import { map } from 'rxjs';
import { MenuItem } from '../models';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-place-order',
  standalone: false,
  templateUrl: './place-order.component.html',
  styleUrl: './place-order.component.css'
})
export class PlaceOrderComponent implements OnInit {

  // TODO: Task 3
  private cartStore = inject(CartStore);
  private router = inject(Router);

  cart$ = this.cartStore.menuItems$;
  cartItems: MenuItem[] = [];
  filteredCart: MenuItem[] = [];

  totalPrice = 0;

  // form 
  private fb = inject(FormBuilder); 
  protected form!: FormGroup; 

  ngOnInit(): void {

    this.cart$.subscribe(items => {
      this.cartItems = items;
      // console.log(">>>items", items);
    })

    // filtered cart with correct items
    // this.filteredCart$ = this.cart$.pipe(
    //   map((data: any) => {
    //     return new Set(data);
    //   })
    // );
    this.filteredCart = this.cartItems.filter((item, i, ar) => ar.indexOf(item) === i);
    this.calculateTotal();

    // form
    this.form = this.createForm();

  }

  calculateTotal() {
    this.filteredCart.forEach((item: MenuItem) => {
      var priceTemp = item.price * item.quantity
      this.totalPrice = this.totalPrice + priceTemp;
    })
  }

  createForm(): FormGroup { 
    return this.fb.group({ 
      username: this.fb.control<string>("", [ Validators.required ]), 
      password: this.fb.control<string>("", [ Validators.required ])
    })
  }

  processForm() { 
    const values = this.form.value; 
    console.log(">>>VALUES: ", values);
  }

  startOver() { 
    // clear component store 
    this.cartStore.resetCart();
    this.router.navigate(['/']);
  }

}
