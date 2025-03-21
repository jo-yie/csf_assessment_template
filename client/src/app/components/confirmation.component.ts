import { Component, inject, OnInit } from '@angular/core';
import { ResponseObject } from '../models';
import { Router } from '@angular/router';
import { CartStore } from '../cart.store';

@Component({
  selector: 'app-confirmation',
  standalone: false,
  templateUrl: './confirmation.component.html',
  styleUrl: './confirmation.component.css'
})
export class ConfirmationComponent implements OnInit {

  // TODO: Task 5
  orderId = sessionStorage.getItem('order_id')
  paymentId = sessionStorage.getItem('payment_id')
  date = sessionStorage.getItem('date')
  total = sessionStorage.getItem('total')

  private router = inject(Router);
  private cartStore = inject(CartStore);

  ngOnInit(): void {

  }

  back() { 
    sessionStorage.clear();
    // clear component store
    this.cartStore.resetCart();
    this.router.navigate(['/']);
  }

}
