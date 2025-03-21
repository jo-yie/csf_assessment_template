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

export interface NewMenuItem { 
    id: string, 
    price: number, 
    quantity: number
}

export interface Order {
    username: string, 
    password: string,
    items: NewMenuItem[]
}

export interface ResponseObject { 
    orderId: string, 
    paymentId: string, 
    total: number,
    timestamp: number
}