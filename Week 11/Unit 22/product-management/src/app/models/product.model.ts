export interface Product {
    id: number;
    name: string;
    price: number;
    status: 'ACTIVE' | 'INACTIVE';
    createdAt: Date;
    updatedAt: Date;
}