import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ProductService } from '../../../services/product.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-product-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './product-form.component.html',
  styleUrls: ['./product-form.component.scss'],
})
export class ProductFormComponent implements OnInit {
  productForm: FormGroup;
  isEdit: boolean = false;
  productId?: number;

  constructor(
    private fb: FormBuilder,
    private productService: ProductService,
    private router: Router,
    private route: ActivatedRoute
  ) {
    this.productForm = this.fb.group({
      name: ['', Validators.required],
      price: [0, Validators.required],
      status: ['ACTIVE', Validators.required],
    });
  }

  ngOnInit(): void {
    this.productId = this.route.snapshot.params['id'];
    this.isEdit = !!this.productId;
    if (this.isEdit) {
      this.productService.getProducts().subscribe(products => {
        const prod = products.find(p => p.id === +this.productId!);
        if (prod) {
          this.productForm.patchValue(prod);
        }
      });
    }
  }

  onSubmit() {
    if (this.productForm.valid) {
      if (this.isEdit) {
        const updatedProduct = { ...this.productForm.value, id: this.productId };
        this.productService.updateProduct(updatedProduct);
      } else {
        this.productService.addProduct(this.productForm.value);
      }
      this.router.navigate(['/products']);
    }
  }

  onCancel() {
    this.router.navigate(['/products']);
  }
}
