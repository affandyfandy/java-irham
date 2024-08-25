import { Component } from '@angular/core';
import { Product } from '../../../models/product.model';
import { ProductService } from '../../../services/product.service';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-product-list',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './product-list.component.html',
  styleUrl: './product-list.component.scss'
})
export class ProductListComponent {
  products: Product[] = [];
  filteredProducts: Product[] = [];
  searchName: string = '';
  sortColumn: string = 'name';
  sortDirection: 'asc' | 'desc' = 'asc';

  constructor(
    private productService: ProductService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.productService.getProducts().subscribe((products) => {
      this.products = products;
      this.filteredProducts = [...products];
    });
  }

  onSearch() {
    this.filteredProducts = this.products.filter((p) =>
      p.name.toLowerCase().includes(this.searchName.toLowerCase())
    );
  }

  onSort(column: keyof Product) {
    this.sortColumn = column;
    this.sortDirection = this.sortDirection === 'asc' ? 'desc' : 'asc';
    this.filteredProducts.sort((a, b) => {
      const comparison = a[column] < b[column] ? -1 : 1;
      return this.sortDirection === 'asc' ? comparison : -comparison;
    });
  }

  editProduct(product: Product) {
    this.router.navigate(['/products/edit', product.id]);
  }

  toggleStatus(product: Product) {
    product.status = product.status === 'ACTIVE' ? 'INACTIVE' : 'ACTIVE';
    this.productService.updateProduct(product);
  }
}
