import { Injectable } from "@angular/core";
import { ComponentStore } from "@ngrx/component-store";
import { Cart, MenuItem } from "./models";

@Injectable()
export class CartStore extends ComponentStore<Cart> {

    constructor() {
        // initialise state with empty array
        super({ menuItems: [] });

    }

    readonly addMenuItem = this.updater((state, menuItem: MenuItem) => ({
        ...state,
        menuItems: [...state.menuItems, menuItem]
    }));

    readonly removeMenuItem = this.updater<string>(
        (store: Cart, itemToDelete: string) => {
            return {
                menuItems: store.menuItems.filter(i => i.name != itemToDelete)
            }
        }
    )

    // num of items in cart
    readonly itemCount$ = this.select((state) => state.menuItems.length);

    // selectors: functions to read state
    readonly menuItems$ = this.select((state) => state.menuItems);

    resetCart() {
        this.setState({menuItems: []});
    }

}