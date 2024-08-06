package com.fpt.MidtermG1.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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
import com.fpt.MidtermG1.dto.RevenueReportDTO;
import com.fpt.MidtermG1.exception.ResourceNotFoundException;
import com.fpt.MidtermG1.service.impl.InvoiceServiceImpl;
import com.fpt.MidtermG1.util.PDFUtils;

@ExtendWith(MockitoExtension.class)
public class InvoiceServiceImplTest {

    @Mock
    private InvoiceRepository invoiceRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private InvoiceProductRepository invoiceProductRepository;

    @Mock
    private PDFUtils pdfUtils;

    @InjectMocks
    private InvoiceServiceImpl invoiceService;

    private Customer customer;
    private Product product;
    private Invoice invoice;
    private InvoiceProduct invoiceProduct;
    private InvoiceDTO invoiceDTO;

    @BeforeEach
    public void setUp() {
        customer = Customer.builder()
                .id(UUID.randomUUID().toString())
                .name("John Doe")
                .phoneNumber("+123456789")
                .status(Status.ACTIVE)
                .createdTime(Timestamp.from(Instant.now()))
                .updatedTime(Timestamp.from(Instant.now()))
                .build();

        product = Product.builder()
                .id(1)
                .name("Product A")
                .price(BigDecimal.valueOf(100.0))
                .status(Status.ACTIVE)
                .createdTime(Timestamp.from(Instant.now()))
                .updatedTime(Timestamp.from(Instant.now()))
                .build();

        invoiceProduct = InvoiceProduct.builder()
                .invoice_id(UUID.randomUUID().toString())
                .product_id(1)
                .quantity(1)
                .price(BigDecimal.valueOf(100.0))
                .amount(BigDecimal.valueOf(100.0))
                .createdTime(Timestamp.from(Instant.now()))
                .updatedTime(Timestamp.from(Instant.now()))
                .invoice(invoice)
                .product(product)
                .build();

        invoice = Invoice.builder()
                .id(UUID.randomUUID().toString())
                .customer(customer)
                .invoiceAmount(BigDecimal.valueOf(100.0))
                .invoiceDate(Timestamp.from(Instant.now()))
                .createdTime(Timestamp.from(Instant.now()))
                .updatedTime(Timestamp.from(Instant.now()))
                .invoiceProducts(new HashSet<>(Collections.singletonList(invoiceProduct)))
                .build();

        invoiceDTO = InvoiceDTO.builder()
                .id(invoice.getId())
                .customer(customer.toDTO())
                .invoiceAmount(invoice.getInvoiceAmount())
                .invoiceDate(invoice.getInvoiceDate())
                .createdTime(invoice.getCreatedTime())
                .updatedTime(invoice.getUpdatedTime())
                .invoiceProducts(Collections.singletonList(invoiceProduct.toDTO()))
                .build();
    }

    @Test
    public void testAddInvoice_Success() {
        when(customerRepository.findById(anyString())).thenReturn(Optional.of(customer));
        when(productRepository.findById(anyInt())).thenReturn(Optional.of(product));
        when(invoiceRepository.save(any(Invoice.class))).thenReturn(invoice);
        when(invoiceProductRepository.save(any(InvoiceProduct.class))).thenReturn(invoiceProduct);

        InvoiceDTO result = invoiceService.addInvoice(invoiceDTO);

        // Verify the interactions
        verify(customerRepository).findById(anyString());
        verify(productRepository).findById(anyInt());
        verify(invoiceRepository).save(any(Invoice.class));

        assertNotNull(result);
        assertEquals(invoice.getId(), result.getId());
    }

    @Test
    public void testAddInvoice_WithInactiveCustomer() {
        when(customerRepository.findById(anyString())).thenReturn(Optional.of(customer));
        when(productRepository.findById(anyInt())).thenReturn(Optional.of(product));
        when(customerRepository.findById(anyString())).thenReturn(Optional.of(customer));
        customer.setStatus(Status.INACTIVE);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            invoiceService.addInvoice(invoiceDTO);
        });

        assertEquals("400 BAD_REQUEST \"Customer is inactive\"", exception.getMessage());
        verify(invoiceRepository, never()).save(any(Invoice.class));
    }

    @Test
    public void testAddInvoice_WithInactiveProduct() {
        product.setStatus(Status.INACTIVE);
        when(customerRepository.findById(anyString())).thenReturn(Optional.of(customer));
        when(productRepository.findById(anyInt())).thenReturn(Optional.of(product));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            invoiceService.addInvoice(invoiceDTO);
        });

        assertTrue(exception.getMessage().contains("Product is inactive"));
        verify(invoiceRepository, never()).save(any(Invoice.class));
    }

    @Test
    public void testEditInvoice() {
        when(invoiceRepository.findById(anyString())).thenReturn(Optional.of(invoice));
        when(customerRepository.findById(anyString())).thenReturn(Optional.of(customer));
        when(productRepository.findById(anyInt())).thenReturn(Optional.of(product));
        when(invoiceRepository.save(any(Invoice.class))).thenReturn(invoice);

        InvoiceDTO result = invoiceService.editInvoice(invoice.getId(), invoiceDTO);

        assertNotNull(result);
        assertEquals(invoiceDTO.getCustomer().getId(), result.getCustomer().getId());
        verify(invoiceProductRepository, times(1)).deleteByInvoice(any(Invoice.class));
        verify(invoiceRepository, times(1)).save(any(Invoice.class));
        verify(invoiceProductRepository, times(invoiceDTO.getInvoiceProducts().size())).save(any(InvoiceProduct.class));
    }

    @Test
    public void testEditInvoice_WithInactiveCustomer() {
        customer.setStatus(Status.INACTIVE);
        when(invoiceRepository.findById(anyString())).thenReturn(Optional.of(invoice));
        when(customerRepository.findById(anyString())).thenReturn(Optional.of(customer));
        when(productRepository.findById(anyInt())).thenReturn(Optional.of(product));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            invoiceService.editInvoice(invoice.getId(), invoiceDTO);
        });

        assertEquals("400 BAD_REQUEST \"Customer is inactive\"", exception.getMessage());
        verify(invoiceRepository, never()).save(any(Invoice.class));
    }

    @Test
    public void testEditInvoice_WithInactiveProduct() {
        product.setStatus(Status.INACTIVE);
        when(invoiceRepository.findById(anyString())).thenReturn(Optional.of(invoice));
        when(customerRepository.findById(anyString())).thenReturn(Optional.of(customer));
        when(productRepository.findById(anyInt())).thenReturn(Optional.of(product));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            invoiceService.editInvoice(invoice.getId(), invoiceDTO);
        });

        assertTrue(exception.getMessage().contains("Product is inactive"));
        verify(invoiceRepository, never()).save(any(Invoice.class));
    }

    @Test
    public void testEditInvoice_AfterAllowedTime() {
        invoice.setInvoiceDate(Timestamp.from(Instant.now().minusSeconds(660))); // Set to 11 minutes ago
        when(invoiceRepository.findById(invoice.getId())).thenReturn(Optional.of(invoice));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            invoiceService.editInvoice(invoice.getId(), invoiceDTO);
        });

        assertEquals("400 BAD_REQUEST \"Invoice can only be edited within 10 minutes of creation\"", exception.getMessage());
        verify(invoiceProductRepository, never()).deleteByInvoice(any(Invoice.class));
        verify(invoiceRepository, never()).save(any(Invoice.class));
    }

    @Test
    public void testGetInvoiceById() {
        when(invoiceRepository.findById(anyString())).thenReturn(Optional.of(invoice));

        InvoiceDTO result = invoiceService.getInvoiceById(invoice.getId());

        assertNotNull(result);
        assertEquals(invoiceDTO.getId(), result.getId());
        verify(invoiceRepository, times(1)).findById(anyString());
    }

    @Test
    public void testGetInvoiceById_NotFound() {
        when(invoiceRepository.findById(anyString())).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            invoiceService.getInvoiceById(invoice.getId());
        });

        assertEquals("Invoice not found with id: " + invoice.getId(), exception.getMessage());
        verify(invoiceRepository, times(1)).findById(anyString());
    }

    @Test
    public void testGetAllInvoices() {
        Page<Invoice> invoicePage = new PageImpl<>(Collections.singletonList(invoice));
        when(invoiceRepository.findAll(any(Pageable.class))).thenReturn(invoicePage);

        List<InvoiceDTO> result = invoiceService.getAllInvoices(0, 10);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(invoiceRepository, times(1)).findAll(any(Pageable.class));
    }

    @Test
    public void testAddInvoiceProduct() {
        // Ensure invoiceProductDTO has a properly set invoice field
        InvoiceProductDTO invoiceProductDTO = InvoiceProductDTO.builder()
                .invoice(invoice.toDTO())
                .product(product.toDTO())
                .quantity(1)
                .price(BigDecimal.valueOf(100.0))
                .amount(BigDecimal.valueOf(100.0))
                .build();

        // Mocking repository behaviors
        when(invoiceRepository.findById(anyString())).thenReturn(Optional.of(invoice));
        when(productRepository.findById(anyInt())).thenReturn(Optional.of(product));
        when(invoiceProductRepository.save(any(InvoiceProduct.class))).thenReturn(invoiceProduct);

        // Execute the service method
        InvoiceProductDTO result = invoiceService.addInvoiceProduct(invoiceProductDTO);

        // Assertions
        assertNotNull(result);
        assertEquals(invoiceProductDTO.getProduct().getId(), result.getProduct().getId());
        verify(invoiceProductRepository, times(1)).save(any(InvoiceProduct.class));
    }

    @Test
    public void testExportAllInvoicesToPDF() {
        List<InvoiceProduct> invoiceProducts = Collections.singletonList(invoiceProduct);
        when(invoiceProductRepository.findAllWithDetails()).thenReturn(invoiceProducts);

        assertDoesNotThrow(() -> invoiceService.exportAllInvoicesToPDF());

        verify(invoiceProductRepository, times(1)).findAllWithDetails();
        try {
            verify(pdfUtils, times(1)).generateAllInvoicesPDF(anyList());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Test
    public void testGetRevenueByPeriod() {
        invoice.setInvoiceDate(Timestamp.valueOf(LocalDateTime.now()));

        int currentYear = LocalDateTime.now().getYear();
        List<RevenueReportDTO> result = invoiceService.getRevenueByPeriod(currentYear, null, null);

        assertNotNull(result);
    }

    @Test
    public void testGetInvoicesByCriteria() {
        Page<Invoice> invoicePage = new PageImpl<>(Collections.singletonList(invoice));
        when(invoiceRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(invoicePage);

        List<InvoiceDTO> result = invoiceService.getInvoicesByCriteria("1", "John", 2023, 5, ">", BigDecimal.valueOf(100), 0, 10);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(invoiceRepository, times(1)).findAll(any(Specification.class), any(Pageable.class));
    }

    @Test
    public void testMatchesPeriod_YearMatch() throws Exception {
        Timestamp invoiceDate = Timestamp.valueOf(LocalDateTime.of(2024, 1, 1, 0, 0));
        assertTrue(invokeMatchesPeriod(invoiceDate, 2024, null, null));
    }

    @Test
    public void testMatchesPeriod_MonthMatch() throws Exception {
        Timestamp invoiceDate = Timestamp.valueOf(LocalDateTime.of(2024, 8, 1, 0, 0));
        assertTrue(invokeMatchesPeriod(invoiceDate, 2024, 8, null));
    }

    @Test
    public void testMatchesPeriod_DayMatch() throws Exception {
        Timestamp invoiceDate = Timestamp.valueOf(LocalDateTime.of(2024, 8, 15, 0, 0));
        assertTrue(invokeMatchesPeriod(invoiceDate, 2024, 8, 15));
    }

    @Test
    public void testMatchesPeriod_YearNoMatch() throws Exception {
        Timestamp invoiceDate = Timestamp.valueOf(LocalDateTime.of(2023, 1, 1, 0, 0));
        assertFalse(invokeMatchesPeriod(invoiceDate, 2024, null, null));
    }

    @Test
    public void testMatchesPeriod_MonthNoMatch() throws Exception {
        Timestamp invoiceDate = Timestamp.valueOf(LocalDateTime.of(2024, 7, 1, 0, 0));
        assertFalse(invokeMatchesPeriod(invoiceDate, 2024, 8, null));
    }

    @Test
    public void testMatchesPeriod_DayNoMatch() throws Exception {
        Timestamp invoiceDate = Timestamp.valueOf(LocalDateTime.of(2024, 8, 14, 0, 0));
        assertFalse(invokeMatchesPeriod(invoiceDate, 2024, 8, 15));
    }

    private boolean invokeMatchesPeriod(Timestamp invoiceDate, Integer year, Integer month, Integer day) throws Exception {
        // Use reflection to access the private method
        java.lang.reflect.Method method = InvoiceServiceImpl.class.getDeclaredMethod("matchesPeriod", Timestamp.class, Integer.class, Integer.class, Integer.class);
        method.setAccessible(true);
        return (boolean) method.invoke(invoiceService, invoiceDate, year, month, day);
    }
}