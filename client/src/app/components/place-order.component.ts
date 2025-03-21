import { Component, inject, OnInit } from '@angular/core';
import { CartStore } from '../cart.store';
import { filter, map } from 'rxjs';
import { Cart, MenuItem, NewMenuItem, Order } from '../models';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { RestaurantService } from '../restaurant.service';

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
  private restaurantService = inject(RestaurantService);

  cart$ = this.cartStore.menuItems$;
  cartItems: MenuItem[] = [];
  filteredCart: MenuItem[] = [];
  // cartToPost: NewMenuItem[] = [];

  cartObject: Cart = { menuItems: [] };

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

    this.filteredCart.forEach((item) => {
      this.cartObject?.menuItems.push(item)
    })

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
      password: this.fb.control<string>("", [ Validators.required ]),
    })
  }

  processForm() { 
    const values = this.form.value; 
    // const values: Order = this.form.value;
    console.log(">>>VALUES: ", values);
    console.log(">>>FILTERED CART: ", this.filteredCart);

    const transformedOrder: Order = {
      username: values.username, 
      password: values.password, 
      items: this.filteredCart.map((item: MenuItem) => ({
        id: item._id,
        price: item.price, 
        quantity: item.quantity
      }))
    };

    console.log(">>>TRANSFORMED ORDER: ", transformedOrder)

    this.restaurantService.postOrder(transformedOrder).subscribe({
      next: (response) => {
        console.log(">>>Response: ", response)
        this.router.navigate(['/confirmation']);
      }, 
      error: (err) => {
        console.log(">>>Error: ", err)
        alert(JSON.stringify(err.error))
      }
    })

    console.log("Order sent to backend");

  }

  startOver() { 
    // clear component store 
    this.cartStore.resetCart();
    this.router.navigate(['/']);
  }

}