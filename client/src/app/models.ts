// You may use this file to create any models

export interface MenuItem {
    _id: string,
    name: string,
    price: number, 
    description: string, 
    quantity: number
}

export interface Cart {
    menuItems: MenuItem[]
}

export interface newMenuItem { 
    id: string, 
    price: number, 
    quantity: number
}