import { Component, inject } from '@angular/core';
import { RestaurantService } from '../restaurant.service';
import { Observable } from 'rxjs';
import { MenuItem } from '../models';
import { CartStore } from '../cart.store';
import { Router } from '@angular/router';

@Component({
  selector: 'app-menu',
  standalone: false,
  templateUrl: './menu.component.html',
  styleUrl: './menu.component.css'
})
export class MenuComponent {

  private restaurantService = inject(RestaurantService);
  private cartStore = inject(CartStore);
  private router = inject(Router);

  // TODO: Task 2
  // get menu from backend
  menu$!: Observable<MenuItem[]>

  temp: any;

  ngOnInit(): void {
    this.menu$ = this.restaurantService.getMenuItems();

  }

  addMenuItem(menuItem: MenuItem): void{
    console.log(">>>Add menu item pressed", menuItem);
    this.cartStore.addMenuItem(menuItem);
    this.updateCount(menuItem);

    this.cartStore.itemCount$
    .subscribe((data) => {
      console.log(">>>Num line items:", data)
    })
  }

  removeMenuItem(menuItem: MenuItem): void{
    console.log(">>>Remove menu item pressed", menuItem);
    this.cartStore.removeMenuItem(menuItem.name); 
    this.updateCount(menuItem);

    this.cartStore.itemCount$
    .subscribe((data) => {
      console.log(">>>Num line items:", data)
    })
  }

  // getCount(character) {
  //   return this.objects.filter(obj => obj.category === character).length;
  // }

  updateCount(menuItem: MenuItem) {
    this.cartStore.menuItems$
      .subscribe((data) => {
        console.log(">>>Menu Item:", menuItem.name,  data.filter(i => i.name === menuItem.name).length)
        menuItem.quantity = data.filter(i => i.name === menuItem.name).length
        // return data.filter(i => i.name === menuItem.name).length
      })
  }

  isCartEmpty() { 
    var length;
    this.cartStore.itemCount$
      .subscribe((data) => {
        length = data;
      })

    if (length == 0) {
      return true;
    }
    return false;
  }

  getItemCount() {
    return this.cartStore.itemCount$;
  }

  getAllItems() { 
    return this.cartStore.menuItems$;
  }

}
