package findo.invoice.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import findo.invoice.data.client.ProductClient;
import findo.invoice.data.entity.Invoice;
import findo.invoice.dto.InvoiceDTO;
import findo.invoice.dto.ProductDTO;

@Mapper(componentModel = "spring", uses = ProductClient.class)
public interface InvoiceMapper {

    InvoiceMapper INSTANCE = Mappers.getMapper(InvoiceMapper.class);

    @Mapping(target = "products", ignore = true)
    InvoiceDTO invoiceToInvoiceDTO(Invoice invoice);

    @Mapping(source = "invoice.productIds", target = "products")
    InvoiceDTO invoiceToInvoiceDTO(Invoice invoice, @Context ProductClient productClient);

    default List<ProductDTO> mapProductIdsToProductDTOs(List<Integer> productIds, @Context ProductClient productClient) {
        return productIds.stream()
                .map(productClient::getProductById)
                .collect(Collectors.toList());
    }
}
