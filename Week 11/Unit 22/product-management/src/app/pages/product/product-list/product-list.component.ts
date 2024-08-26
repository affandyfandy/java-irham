import { Component, OnInit } from '@angular/core';
import { Product } from '../../../models/product.model';
import { ProductService } from '../../../services/product.service';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ProductFormComponent } from '../product-form/product-form.component';

@Component({
  selector: 'app-product-list',
  standalone: true,
  imports: [FormsModule, CommonModule, ProductFormComponent],
  templateUrl: './product-list.component.html',
  styleUrl: './product-list.component.scss'
})
export class ProductListComponent implements OnInit {
  products: Product[] = [];
  filteredProducts: Product[] = [];
  searchName: string = '';
  sortColumn: string = 'name';
  sortDirection: 'asc' | 'desc' = 'asc';
  isModalVisible: boolean = false;
  selectedProduct: Product | null = null;

  // Pagination properties
  currentPage: number = 1;
  itemsPerPage: number = 10;

  constructor(
    private productService: ProductService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.productService.getProducts().subscribe((products) => {
      this.products = products;
      this.updateFilteredProducts();
    });
  }

  addProduct(): void {
    this.selectedProduct = null;
    this.isModalVisible = true;
  }

  editProduct(product: Product): void {
    this.selectedProduct = product;
    this.isModalVisible = true;
  }

  onSave(product: Product): void {
    if (this.selectedProduct) {
      const index = this.products.findIndex(p => p.id === this.selectedProduct!.id);
      this.products[index] = product;
    } else {
      this.products.push(product);
    }
    this.updateFilteredProducts();
    this.isModalVisible = false;
  }

  onCancel(): void {
    this.isModalVisible = false;
  }

  onSort(column: keyof Product) {
    this.sortColumn = column;
    this.sortDirection = this.sortDirection === 'asc' ? 'desc' : 'asc';
    this.updateFilteredProducts();
  }

  updateFilteredProducts(): void {
    this.filteredProducts = this.products
      .filter(product => product.name.toLowerCase().includes(this.searchName.toLowerCase()))
      .sort((a, b) => {
        const comparison = (a as any)[this.sortColumn] < (b as any)[this.sortColumn] ? -1 : 1;
        return this.sortDirection === 'asc' ? comparison : -comparison;
      })
      .slice((this.currentPage - 1) * this.itemsPerPage, this.currentPage * this.itemsPerPage);
  }

  toggleStatus(product: Product) {
    product.status = product.status === 'ACTIVE' ? 'INACTIVE' : 'ACTIVE';
    this.productService.updateProduct(product);
  }

  onPageChange(page: number): void {
    this.currentPage = page;
    this.updateFilteredProducts();
  }
}
