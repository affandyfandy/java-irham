package findo.invoice.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import findo.invoice.data.client.ProductClient;
import findo.invoice.data.entity.Invoice;
import findo.invoice.data.repository.InvoiceRepository;
import findo.invoice.dto.InvoiceDTO;
import findo.invoice.dto.ProductDTO;
import findo.invoice.mapper.InvoiceMapper;
import findo.invoice.service.InvoiceService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class InvoiceServiceImpl implements InvoiceService {
    
    final private InvoiceRepository invoiceRepository;
    final private ProductClient productClient;
    final private RestTemplate restTemplate;
    final private WebClient.Builder webClientBuilder;
    final private InvoiceMapper invoiceMapper;

    // With Feign Client
    @Override
    public List<InvoiceDTO> getAllInvoicesWithFeignClient() {
        List<Invoice> invoices = invoiceRepository.findAll();
        return invoices.stream()
                .map(invoice -> invoiceMapper.invoiceToInvoiceDTO(invoice, productClient))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<InvoiceDTO> getInvoiceByIdWithFeignClient(int id) {
        Invoice invoice = invoiceRepository.findById(id).orElse(null);
        if (invoice == null) {
            return null;
        }

        return Optional.ofNullable(invoiceMapper.invoiceToInvoiceDTO(invoice, productClient));
    }

    @Override
    public Invoice createInvoiceWithFeignClient(Invoice invoice) {
        double totalAmount = invoice.getProductIds().stream()
                    .map(productClient::getProductById)
                    .mapToDouble(ProductDTO::getPrice)
                    .sum();
        
        invoice.setTotalAmount(totalAmount);
        return invoiceRepository.save(invoice);
    }

    // With Rest Template
    @Override
    public List<InvoiceDTO> getAllInvoicesWithRestTemplate() {
        List<Invoice> invoices = invoiceRepository.findAll();
        return invoices.stream()
                .map(invoice -> {
                    List<ProductDTO> products = invoice.getProductIds().stream()
                            .map(id -> restTemplate.getForObject("http://localhost:8081/api/v1/products/{id}", ProductDTO.class, id))
                            .collect(Collectors.toList());
                    InvoiceDTO invoiceDTO = invoiceMapper.invoiceToInvoiceDTO(invoice);
                    invoiceDTO.setProducts(products);
                    return invoiceDTO;
                })
                .collect(Collectors.toList());
    }

    @Override
    public Optional<InvoiceDTO> getInvoiceByIdWithRestTemplate(int id) {
        Invoice invoice = invoiceRepository.findById(id).orElse(null);
        if (invoice == null) {
            return Optional.empty();
        }
        List<ProductDTO> products = invoice.getProductIds().stream()
                .map(productId -> restTemplate.getForObject("http://localhost:8081/api/v1/products/{id}", ProductDTO.class, productId))
                .collect(Collectors.toList());
        InvoiceDTO invoiceDTO = invoiceMapper.invoiceToInvoiceDTO(invoice);
        invoiceDTO.setProducts(products);
        return Optional.of(invoiceDTO);
    }

    @Override
    public Invoice createInvoiceWithRestTemplate(Invoice invoice) {
        double totalAmount = invoice.getProductIds().stream()
                .map(id -> restTemplate.getForObject("http://localhost:8081/api/v1/products/{id}", ProductDTO.class, id))
                .mapToDouble(ProductDTO::getPrice)
                .sum();
        invoice.setTotalAmount(totalAmount);
        return invoiceRepository.save(invoice);
    }

    @Override
    public List<InvoiceDTO> getAllInvoicesWithWebClient() {
        WebClient webClient = webClientBuilder.baseUrl("http://localhost:8081/api/v1").build();
        List<Invoice> invoices = invoiceRepository.findAll();
        return invoices.stream()
                .map(invoice -> {
                    List<ProductDTO> products = invoice.getProductIds().stream()
                            .map(id -> webClient.get()
                                    .uri("/products/{id}", id)
                                    .retrieve()
                                    .bodyToMono(ProductDTO.class)
                                    .block())
                            .collect(Collectors.toList());
                    InvoiceDTO invoiceDTO = invoiceMapper.invoiceToInvoiceDTO(invoice);
                    invoiceDTO.setProducts(products);
                    return invoiceDTO;
                })
                .collect(Collectors.toList());
    }

    @Override
    public Optional<InvoiceDTO> getInvoiceByIdWithWebClient(int id) {
        Invoice invoice = invoiceRepository.findById(id).orElse(null);
        if (invoice == null) {
            return Optional.empty();
        }
        WebClient webClient = webClientBuilder.baseUrl("http://localhost:8081/api/v1").build();
        List<ProductDTO> products = invoice.getProductIds().stream()
                .map(productId -> webClient.get()
                        .uri("/products/{id}", productId)
                        .retrieve()
                        .bodyToMono(ProductDTO.class)
                        .block())
                .collect(Collectors.toList());
        InvoiceDTO invoiceDTO = invoiceMapper.invoiceToInvoiceDTO(invoice);
        invoiceDTO.setProducts(products);
        return Optional.of(invoiceDTO);
    }  

    @Override
    public Invoice createInvoiceWithWebClient(Invoice invoice) {
        WebClient webClient = webClientBuilder.baseUrl("http://localhost:8081/api/v1").build();
        double totalAmount = invoice.getProductIds().stream()
                .map(id -> webClient.get()
                        .uri("/products/{id}", id)
                        .retrieve()
                        .bodyToMono(ProductDTO.class)
                        .block()
                        .getPrice())
                .mapToDouble(Double::doubleValue)
                .sum();
        invoice.setTotalAmount(totalAmount);
        return invoiceRepository.save(invoice);
    }  
}
