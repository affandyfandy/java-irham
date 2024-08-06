package com.fpt.MidtermG1.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.fpt.MidtermG1.common.Status;
import com.fpt.MidtermG1.data.entity.Customer;
import com.fpt.MidtermG1.data.entity.Invoice;
import com.fpt.MidtermG1.data.entity.InvoiceProduct;
import com.fpt.MidtermG1.data.entity.Product;
import com.fpt.MidtermG1.data.repository.CustomerRepository;
import com.fpt.MidtermG1.data.repository.InvoiceProductRepository;
import com.fpt.MidtermG1.data.repository.InvoiceRepository;
import com.fpt.MidtermG1.data.repository.ProductRepository;
import com.fpt.MidtermG1.dto.InvoiceDTO;
import com.fpt.MidtermG1.dto.InvoiceProductDTO;
import com.fpt.MidtermG1.dto.ProductDTO;
import com.fpt.MidtermG1.dto.RevenueReportDTO;
import com.fpt.MidtermG1.exception.InactiveCustomerException;
import com.fpt.MidtermG1.exception.InactiveProductException;
import com.fpt.MidtermG1.exception.ResourceNotFoundException;
import com.fpt.MidtermG1.service.InvoiceService;
import com.fpt.MidtermG1.specifications.InvoiceSpecificationsBuilder;
import com.fpt.MidtermG1.util.PDFUtils;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class InvoiceServiceImpl implements InvoiceService {
    private final InvoiceRepository invoiceRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final InvoiceProductRepository invoiceProductRepository;
    private final PDFUtils pdfUtils;

    @Override
    @Transactional
    public InvoiceDTO addInvoice(InvoiceDTO invoiceDTO) {
        List<InvoiceProductDTO> invoiceProducts = invoiceDTO.getInvoiceProducts();
        BigDecimal invoiceAmount = BigDecimal.ZERO;
        Set<String> inactiveProductIds = new HashSet<>();

        for (InvoiceProductDTO invoiceProduct : invoiceProducts) {
            Product product = productRepository.findById(invoiceProduct.getProduct().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

            if (product.getStatus() != Status.ACTIVE) {
                inactiveProductIds.add(String.valueOf(product.getId()));
            }

            BigDecimal price = product.getPrice();
            int quantity = invoiceProduct.getQuantity();
            BigDecimal amount = price.multiply(BigDecimal.valueOf(quantity));

            invoiceProduct.setProduct(ProductDTO.builder()
                    .id(product.getId())
                    .name(product.getName())
                    .price(product.getPrice())
                    .status(product.getStatus())
                    .createdTime(product.getCreatedTime())
                    .updatedTime(product.getUpdatedTime())
                    .build());
            invoiceProduct.setPrice(price);
            invoiceProduct.setAmount(amount);

            invoiceAmount = invoiceAmount.add(amount);
        }

        Customer customer = customerRepository.findById(invoiceDTO.getCustomer().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

        if (customer.getStatus() != Status.ACTIVE) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Customer is inactive");
        }

        if (!inactiveProductIds.isEmpty()) {
            String message = generateInactiveProductMessage(inactiveProductIds);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, message);
        }

        Invoice invoice = invoiceDTO.toEntity();
        invoice.setInvoiceAmount(invoiceAmount);
        invoice.setInvoiceDate(Timestamp.from(Instant.now()));
        invoice.setCreatedTime(Timestamp.from(Instant.now()));
        invoice.setUpdatedTime(Timestamp.from(Instant.now()));
        invoice.setCustomer(customer);

        Invoice savedInvoice = invoiceRepository.save(invoice);

        for (InvoiceProductDTO invoiceProductDTO : invoiceDTO.getInvoiceProducts()) {
            InvoiceProduct invoiceProduct = invoiceProductDTO.toEntity();
            invoiceProduct.setInvoice(savedInvoice);
            invoiceProduct.setInvoice_id(savedInvoice.getId());
            invoiceProduct.setProduct_id(invoiceProductDTO.getProduct().getId());
            invoiceProductRepository.save(invoiceProduct);
        }

        return savedInvoice.toDTO();
    }

    @Override
    @Transactional
    public InvoiceDTO editInvoice(String id, InvoiceDTO invoiceDTO) {
        Invoice existingInvoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Invoice not found"));

        Timestamp currentTime = Timestamp.from(Instant.now());
        Duration duration = Duration.between(existingInvoice.getInvoiceDate().toInstant(), currentTime.toInstant());
        if (duration.toMinutes() > 10) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Invoice can only be edited within 10 minutes of creation");
        }

        invoiceProductRepository.deleteByInvoice(existingInvoice);

        List<InvoiceProductDTO> invoiceProducts = invoiceDTO.getInvoiceProducts();
        BigDecimal invoiceAmount = BigDecimal.ZERO;
        Set<String> inactiveProductIds = new HashSet<>();

        for (InvoiceProductDTO invoiceProduct : invoiceProducts) {
            Product product = productRepository.findById(invoiceProduct.getProduct().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

            if (product.getStatus() != Status.ACTIVE) {
                inactiveProductIds.add(String.valueOf(product.getId()));
            }

            BigDecimal price = product.getPrice();
            int quantity = invoiceProduct.getQuantity();
            BigDecimal amount = price.multiply(BigDecimal.valueOf(quantity));

            invoiceProduct.setPrice(price);
            invoiceProduct.setAmount(amount);

            invoiceAmount = invoiceAmount.add(amount);
        }

        Customer customer = customerRepository.findById(invoiceDTO.getCustomer().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

        if (customer.getStatus() != Status.ACTIVE) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Customer is inactive");
        }

        if (!inactiveProductIds.isEmpty()) {
            String message = generateInactiveProductMessage(inactiveProductIds);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, message);
        }

        existingInvoice.setCustomer(customer);
        existingInvoice.setInvoiceAmount(invoiceAmount);
        existingInvoice.setUpdatedTime(Timestamp.from(Instant.now()));

        Invoice savedInvoice = invoiceRepository.save(existingInvoice);

        for (InvoiceProductDTO invoiceProductDTO : invoiceDTO.getInvoiceProducts()) {
            InvoiceProduct invoiceProduct = invoiceProductDTO.toEntity();
            invoiceProduct.setInvoice(savedInvoice);
            invoiceProduct.setInvoice_id(savedInvoice.getId());
            invoiceProduct.setProduct_id(invoiceProductDTO.getProduct().getId());
            invoiceProductRepository.save(invoiceProduct);
        }

        return savedInvoice.toDTO();
    }

    private String generateInactiveProductMessage(Set<String> inactiveProductIds) {
        if (inactiveProductIds.size() == 1) {
            return "Product " + inactiveProductIds.iterator().next() + " is inactive";
        } else {
            String ids = String.join(", ", inactiveProductIds);
            return "Products " + ids + " are inactive";
        }
    }

    private String generateInactiveProductMessage(Product product) {
        return "Product with id: " + product.getId() + " is inactive.";
    }

    @Override
    public InvoiceDTO getInvoiceById(String id) {
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Invoice not found with id: " + id));
        return invoice.toDTO();
    }

    @Override
    public List<InvoiceDTO> getAllInvoices(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Invoice> invoices = invoiceRepository.findAll(pageable);
        return invoices.getContent().stream()
                .map(Invoice::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public InvoiceProductDTO addInvoiceProduct(InvoiceProductDTO invoiceProductDTO) {
        InvoiceProduct invoiceProduct = invoiceProductDTO.toEntity();
        Invoice invoice = invoiceRepository.findById(invoiceProductDTO.getInvoice().getId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Invoice not found with id: " + invoiceProductDTO.getInvoice().getId()));
        Product product = productRepository.findById(invoiceProductDTO.getProduct().getId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Product not found with id: " + invoiceProductDTO.getProduct().getId()));

        if (invoice.getCustomer().getStatus() != Status.ACTIVE) {
            throw new InactiveCustomerException("Customer is inactive");
        }

        if (product.getStatus() != Status.ACTIVE) {
            throw new InactiveProductException("Product is inactive");
        }

        invoiceProduct.setInvoice(invoice);
        invoiceProduct.setProduct(product);
        invoiceProduct
                .setAmount(invoiceProductDTO.getPrice().multiply(new BigDecimal(invoiceProductDTO.getQuantity())));
        invoiceProduct = invoiceProductRepository.save(invoiceProduct);
        return invoiceProduct.toDTO();
    }

    @Override
    public List<InvoiceDTO> getInvoicesByCriteria(String customerId, String customerName, int year, int month,
            String invoiceAmountCondition, BigDecimal invoiceAmount, int page, int size) {
        InvoiceSpecificationsBuilder builder = new InvoiceSpecificationsBuilder();

        if (customerId != null && !customerId.isEmpty()) {
            builder.with("customer.id", ":", customerId);
        }
        if (customerName != null && !customerName.isEmpty()) {
            builder.with("customer.name", ":", customerName);
        }
        if (year > 0) {
            builder.with("year", ":", year);
        }
        if (month > 0) {
            builder.with("month", ":", month);
        }
        if (invoiceAmountCondition != null && !invoiceAmountCondition.isEmpty() && invoiceAmount != null) {
            builder.with("invoiceAmount", invoiceAmountCondition, invoiceAmount);
        }

        Specification<Invoice> spec = builder.build();
        Pageable pageable = PageRequest.of(page, size);

        return invoiceRepository.findAll(spec, pageable)
                .stream()
                .map(Invoice::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public byte[] exportAllInvoicesToPDF() {
        List<InvoiceProduct> invoiceProducts = invoiceProductRepository.findAllWithDetails();
        try {
            return pdfUtils.generateAllInvoicesPDF(invoiceProducts);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Failed to export the PDF: " + e.getMessage());
        }
    }

    @Override
    public List<RevenueReportDTO> getRevenueByPeriod(Integer year, Integer month, Integer day) {
        Timestamp startDate = null;
        Timestamp endDate = null;

        if (year != null) {
            LocalDateTime start = LocalDate.of(year, month != null ? month : 1, day != null ? day : 1).atStartOfDay();
            LocalDateTime end = start.plusMonths(month != null ? 1 : 12).minusNanos(1);
            startDate = Timestamp.valueOf(start);
            endDate = Timestamp.valueOf(end);
        }

        List<Invoice> invoices;
        if (startDate != null && endDate != null) {
            invoices = invoiceRepository.findByInvoiceDateBetween(startDate, endDate);
        } else {
            invoices = invoiceRepository.findAll();
        }

        Map<String, BigDecimal> revenueMap = new HashMap<>();

        for (Invoice invoice : invoices) {
            Timestamp invoiceDate = invoice.getInvoiceDate();
            if (matchesPeriod(invoiceDate, year, month, day)) {
                String dateKey = invoiceDate.toLocalDateTime().toLocalDate().toString();
                revenueMap.put(dateKey,
                        revenueMap.getOrDefault(dateKey, BigDecimal.ZERO).add(invoice.getInvoiceAmount()));
            }
        }

        List<RevenueReportDTO> report = new ArrayList<>();
        for (Map.Entry<String, BigDecimal> entry : revenueMap.entrySet()) {
            RevenueReportDTO dto = new RevenueReportDTO();
            dto.setDate(LocalDate.parse(entry.getKey()));
            dto.setRevenue(entry.getValue());
            report.add(dto);
        }

        return report;
    }

    private boolean matchesPeriod(Timestamp invoiceDate, Integer year, Integer month, Integer day) {
        if (year != null && !(invoiceDate.toLocalDateTime().toLocalDate().getYear() == year)) {
            return false;
        }
        if (month != null && !(invoiceDate.toLocalDateTime().toLocalDate().getMonthValue() == month)) {
            return false;
        }
        if (day != null && !(invoiceDate.toLocalDateTime().toLocalDate().getDayOfMonth() == day)) {
            return false;
        }
        return true;
    }
}
