import { Component, inject } from '@angular/core';
import { RestaurantService } from '../restaurant.service';
import { Observable } from 'rxjs';
import { MenuItem } from '../models';

@Component({
  selector: 'app-menu',
  standalone: false,
  templateUrl: './menu.component.html',
  styleUrl: './menu.component.css'
})
export class MenuComponent {

  private restaurantService = inject(RestaurantService);

  // TODO: Task 2
  // get menu from backend
  menu$!: Observable<MenuItem[]>

  ngOnInit(): void {
    this.menu$ = this.restaurantService.getMenuItems();

  }

}
