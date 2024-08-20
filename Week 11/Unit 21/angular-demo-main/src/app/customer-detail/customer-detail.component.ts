import { Component, Input, OnInit } from '@angular/core';
import { Customer } from '../models/customer.model';
import { CustomerService } from '../services/customer.service';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-customer-detail',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    RouterLink
  ],
  templateUrl: './customer-detail.component.html',
  styleUrl: './customer-detail.component.scss'
})
export class CustomerDetailComponent implements OnInit {
  @Input() viewMode = false;

  @Input() currentCustomer: Customer = {
    id: '',
    firstname: '',
    gender: false
  };

  message = '';

  constructor(
    private customerService: CustomerService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    if (!this.viewMode) {
      this.message = '';
      this.getCustomer(this.route.snapshot.params['id']);
    }
  }

  getCustomer(id: string): void {
    this.customerService.get(id).subscribe({
      next: (data) => {
        this.currentCustomer = data;
        console.log(data);
      },
      error: (e) => console.error(e)
    });
  }

  updateCustomer(): void {
    this.message = '';

    this.customerService
      .update(this.currentCustomer.id, this.currentCustomer)
      .subscribe({
        next: (res) => {
          console.log(res);
          this.message = res.message
            ? res.message
            : 'This customer was updated successfully!';
        },
        error: (e) => console.error(e)
      });
  }

  deleteCustomer(): void {
    this.customerService.delete(this.currentCustomer.id).subscribe({
      next: (res) => {
        console.log(res);
        this.router.navigate(['/customers']);
      },
      error: (e) => console.error(e)
    });
  }
}
